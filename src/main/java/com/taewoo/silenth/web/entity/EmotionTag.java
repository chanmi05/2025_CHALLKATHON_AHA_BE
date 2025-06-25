package com.taewoo.silenth.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class EmotionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    // 시각화를 위한 감정 색상
    private String colorHex;

    public EmotionTag(String tagName){
        this.tagName = tagName;
    }
}