package com.team2.levelog.image.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String imageFile;
}
