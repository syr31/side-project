package com.hodolog.request.comment;

import lombok.Getter;

@Getter
public class CommentDelete {
    private String password;


    public CommentDelete(String password) {
        this.password = password;
    }
}
