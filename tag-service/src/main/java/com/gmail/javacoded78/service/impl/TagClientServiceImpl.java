package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.model.Tag;
import com.gmail.javacoded78.model.TweetTag;
import com.gmail.javacoded78.repository.TagRepository;
import com.gmail.javacoded78.repository.TweetTagRepository;
import com.gmail.javacoded78.service.TagClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagClientServiceImpl implements TagClientService {

    private final TagRepository tagRepository;
    private final TweetTagRepository tweetTagRepository;

    @Override
    public List<String> getTagsByText(String text) {
        return tagRepository.getTagsByText(text);
    }

    @Override
    @Transactional
    public void parseHashtagsFromText(Long tweetId, String text) {
        Pattern pattern = Pattern.compile("(#\\w+)\\b");
        Matcher match = pattern.matcher(text);
        List<String> hashtags = new ArrayList<>();

        while (match.find()) {
            hashtags.add(match.group(1));
        }

        hashtags.forEach(hashtag -> {
            Optional<Tag> maybeTag = tagRepository.findByTagName(hashtag);
            maybeTag.ifPresentOrElse(
                    tag -> saveAndUpdateTweetTag(tweetId, tag),
                    () -> saveTweetTag(tweetId, hashtag)
            );
        });
    }

    private void saveTweetTag(Long tweetId, String hashtag) {
        Tag newTag = tagRepository.save(Tag.builder()
                .tagName(hashtag)
                .build());
        TweetTag tweetTag = buildTweetTag(tweetId, newTag.getId());
        tweetTagRepository.save(tweetTag);
    }

    private void saveAndUpdateTweetTag(Long tweetId, Tag tag) {
        Long id = tag.getId();
        TweetTag tweetTag = buildTweetTag(tweetId, id);
        tagRepository.updateTagQuantity(id, true);
        tweetTagRepository.save(tweetTag);
    }

    private TweetTag buildTweetTag(Long tweetId, Long tagId) {
        return TweetTag.builder()
                .tagId(tagId)
                .tweetId(tweetId)
                .build();
    }

    @Override
    @Transactional
    public void deleteTagsByTweetId(Long tweetId) {
        List<Long> tagsIds = tweetTagRepository.getTagIdsByTweetId(tweetId);
        List<Tag> tags = tagRepository.getTagsByIds(tagsIds);
        tags.forEach(tag -> {
            if (tag.getTweetsQuantity() - 1 == 0) {
                tagRepository.delete(tag);
                tweetTagRepository.deleteTag(tag.getId());
            } else {
                tagRepository.updateTagQuantity(tag.getId(), false);
            }
        });
    }
}
