package com.hodolog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(
        indexes = {
                @Index(name = "IDX_COMMENT_POST_ID", columnList = "post_id")
        }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn
    private Post post;

    @Builder
    public Comment(String author, String password, String content) {
        this.author = author;
        this.password = password;
        this.content = content;
    }

    public void setPost(Post post){
        this.post = post;
    }
}
