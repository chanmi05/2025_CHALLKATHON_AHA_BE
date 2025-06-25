package com.taewoo.silenth.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SilentPostEmotionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // SilentPost와 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "silent_post_id", nullable = false)
    private SilentPost silentPost;

    // EmotionTag와 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_tag_id", nullable = false)
    private EmotionTag emotionTag;

    public SilentPostEmotionTag(SilentPost post, EmotionTag tag) {
        this.silentPost = post;
        this.emotionTag = tag;
    }

    public void setSilentPost(SilentPost post) {
        this.silentPost = post;
    }
    public void setEmotionTag(EmotionTag tag) {
        this.emotionTag = tag;
    }

}