package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.projection.ProfileTweetImageProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.id = :tweetId
            """)
    <T> Optional<T> getTweetById(@Param("tweetId") Long tweetId, Class<T> type);

    @Query("""
            SELECT DISTINCT tweet.authorId FROM Tweet tweet
            WHERE tweet.addressedUsername IS NULL
                AND tweet.scheduledDate IS NULL
                AND tweet.deleted = false
            """)
    List<Long> getTweetAuthorIds();

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId IN :userIds
                AND t.addressedUsername IS NULL
                AND t.scheduledDate IS NULL
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    Page<TweetProjection> getTweetsByAuthorIds(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId = :userId
                AND t.addressedUsername IS NULL
                AND t.scheduledDate IS NULL
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    List<TweetUserProjection> getTweetsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT t FROM Tweet t
            LEFT JOIN t.images i
            WHERE t.scheduledDate IS NULL
                AND t.deleted = false
                AND (t.authorId = :userId
                    AND i.id IS NOT NULL)
                OR t.scheduledDate IS NULL
                AND t.deleted = false
            AND (t.authorId = :userId
                AND t.text LIKE CONCAT('%','youtu','%'))
            ORDER BY t.dateTime DESC
            """)
    Page<TweetProjection> getUserMediaTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId = :userId
                AND t.addressedUsername IS NOT NULL
                AND t.scheduledDate IS NULL
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    List<TweetUserProjection> getRepliesByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.addressedTweetId = :tweetId
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    List<TweetProjection> getRepliesByTweetId(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT t FROM Tweet t
            LEFT JOIN t.quoteTweet qt
            WHERE t.authorId IN :userIds
                AND qt.id = :tweetId
                AND qt.deleted = false
            """)
    Page<TweetProjection> getQuotesByTweetId(@Param("userIds") List<Long> userIds,
                                             @Param("tweetId") Long tweetId,
                                             Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId IN :userIds
                AND t.scheduledDate IS NULL
                AND size(t.images) <> 0
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    Page<TweetProjection> getMediaTweets(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId IN :userIds
                AND t.scheduledDate IS NULL
                AND t.deleted = false
                AND t.text LIKE CONCAT('%','youtu','%')
            """)
    Page<TweetProjection> getTweetsWithVideo(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId IN :userIds
                AND t.addressedUsername IS NULL
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    Page<TweetProjection> getFollowersTweets(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId = :userId
                AND t.scheduledDate IS NOT NULL
                AND t.deleted = false
            ORDER BY t.scheduledDate DESC
            """)
    Page<TweetProjection> getScheduledTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.authorId = :userId
                AND t.id = :tweetId
            """)
    Optional<Tweet> getTweetByUserId(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.scheduledDate IS NULL
                AND t.deleted = false
                AND (t.text LIKE CONCAT('%',:text,'%')
                    AND t.authorId IN :userIds
                    OR t.authorId IN :userIds)
            ORDER BY t.dateTime DESC
            """)
    Page<TweetProjection> searchTweets(@Param("text") String text, @Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT t.authorId FROM Tweet t
            WHERE t.text LIKE CONCAT('%',:text,'%')
            """)
    List<Long> getUserIdsByTweetText(@Param("text") String text);

    @Modifying
    @Query(value = """
                    INSERT INTO replies (tweet_id, reply_id)
                    VALUES (?1, ?2)
                    """, nativeQuery = true)
    void addReply(@Param("tweetId") Long tweetId, @Param("replyId") Long replyId);

    @Modifying
    @Query(value = """
                    INSERT INTO quotes (tweet_id, quote_id)
                    VALUES (?1, ?2)
                    """, nativeQuery = true)
    void addQuote(@Param("tweetId") Long tweetId, @Param("quoteTweetId") Long quoteTweetId);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.id = :tweetId
                AND t.authorId = :userId
            """)
    Optional<Tweet> getTweetByAuthorIdAndTweetId(@Param("tweetId") Long tweetId, @Param("userId") Long userId);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.id = :tweetId
                AND t.poll.id = :pollId
            """)
    Optional<Tweet> getTweetByPollIdAndTweetId(@Param("tweetId") Long tweetId, @Param("pollId") Long pollId);

    @Query("""
            SELECT CASE WHEN count(t) > 0 THEN true
                ELSE false END
            FROM Tweet t
            WHERE t.id = :tweetId
            """)
    boolean isTweetExists(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.id IN :tweetIds
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    List<TweetProjection> getTweetListsByIds(@Param("tweetIds") List<Long> tweetIds);

    @Query("""
            SELECT t FROM Tweet t
            WHERE t.id IN :tweetIds
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    Page<TweetProjection> getTweetsByIds(@Param("tweetIds") List<Long> tweetIds, Pageable pageable);

    @Query("""
            SELECT t.id AS tweetId, i.id AS imageId, i.src AS src FROM Tweet t
            LEFT JOIN t.images i
            WHERE t.scheduledDate IS NULL
                AND t.authorId = :userId
                AND i.id IS NOT NULL
                AND t.deleted = false
            ORDER BY t.dateTime DESC
            """)
    List<ProfileTweetImageProjection> getUserTweetImages(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT COUNT(t) FROM Tweet t
            WHERE t.scheduledDate IS NULL
                AND t.deleted = false
                AND UPPER(t.text) LIKE UPPER(CONCAT('%',:text,'%'))
            """)
    Long getTweetCountByText(@Param("text") String text);

    @Query("""
            SELECT tg FROM Tweet t
            LEFT JOIN t.taggedImageUsers tg
            WHERE t.id = :tweetId
            """)
    List<Long> getTaggedImageUserIds(@Param("tweetId") Long tweetId);

    List<Tweet> findAllByScheduledDate(LocalDateTime now);
}
