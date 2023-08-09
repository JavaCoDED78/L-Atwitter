package com.gmail.javacoded78.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "gif_image")
public class GifImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gif_image_seq")
    @SequenceGenerator(name = "gif_image_seq", sequenceName = "gif_image_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "width")
    private Long width;

    @Column(name = "height")
    private Long height;
}
