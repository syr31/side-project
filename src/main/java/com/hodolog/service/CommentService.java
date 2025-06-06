package com.hodolog.service;

import com.hodolog.domain.Comment;
import com.hodolog.domain.Post;
import com.hodolog.exception.CommentNotFound;
import com.hodolog.exception.InvalidPassword;
import com.hodolog.exception.PostNotFound;
import com.hodolog.repository.comment.CommentRepository;
import com.hodolog.repository.post.PostRepository;
import com.hodolog.request.comment.CommentCreate;
import com.hodolog.request.comment.CommentDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, CommentCreate request) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .password(encryptedPassword)
                .content(request.getPassword())
                .build();

        post.addComment(comment);

    }

    public void delete(Long commentId, CommentDelete request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFound::new);

        String encryptPassword = comment.getPassword();

        if(!passwordEncoder.matches(request.getPassword(), encryptPassword)){
            throw new InvalidPassword();
        }

        commentRepository.delete(comment);
    }
}
