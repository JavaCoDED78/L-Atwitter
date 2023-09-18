package com.gmail.javacoded78.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tweet_images")
public class TweetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweet_image_seq")
    @SequenceGenerator(name = "tweet_image_seq", sequenceName = "tweet_image_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "src")
    private String src;
}
