package com.taewoo.silenth.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // 👇 이 부분이 누락되어 있었습니다. OneToMany 관계를 추가합니다.
    @OneToMany(mappedBy = "emotionTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SilentPostEmotionTag> postTags = new ArrayList<>();


    public EmotionTag(String tagName){
        this.tagName = tagName;
    }
}
