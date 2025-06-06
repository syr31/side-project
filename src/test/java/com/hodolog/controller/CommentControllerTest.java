package com.hodolog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.config.MockUser;
import com.hodolog.domain.Comment;
import com.hodolog.domain.Post;
import com.hodolog.domain.User;
import com.hodolog.repository.UserRepository;
import com.hodolog.repository.comment.CommentRepository;
import com.hodolog.repository.post.PostRepository;
import com.hodolog.request.comment.CommentCreate;
import com.hodolog.request.comment.CommentDelete;
import com.hodolog.request.post.PostCreate;
import com.hodolog.request.post.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @AfterEach
    void clean(){
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @MockUser
    @DisplayName("댓글 작성")
    void test1() throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .name("test")
                .password("1234")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        postRepository.save(post);

        CommentCreate request = CommentCreate.builder()
                .author("test")
                .password("123456")
                .content("댓글이다아아아아아아임마")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제")
    void test2() throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .name("test")
                .password("1234")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        postRepository.save(post);

        String encryptedPassword = passwordEncoder.encode("123456");

        Comment comment = Comment.builder()
                .author("test")
                .password(encryptedPassword)
                .content("qwer1234qwer1234")
                .build();

        comment.setPost(post);
        commentRepository.save(comment);

        CommentDelete request = new CommentDelete("123456");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
    }