package com.hodolog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.config.MockUser;
import com.hodolog.domain.Post;
import com.hodolog.domain.User;
import com.hodolog.repository.post.PostRepository;
import com.hodolog.repository.UserRepository;
import com.hodolog.request.post.PostCreate;
import com.hodolog.request.post.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    @AfterEach
    void clean(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @MockUser
    @DisplayName("글 작성 요청시 title 값은 필수")
    void test2() throws Exception {
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("타이틀을 입력해주세요."));

    }

    @Test
    @MockUser
    @DisplayName("글 작성")
    void test3() throws Exception {
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(1l, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bar"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .name("test")
                .password("1234")
                .build();

        userRepository.save(user);

        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(10)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(30)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("제목 30")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("내용 30")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .name("test")
                .password("1234")
                .build();

        userRepository.save(user);

        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(10)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(30)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("제목 30")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("내용 30")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @MockUser
    @DisplayName("글 제목 수정")
    void test7() throws Exception {
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목 수정")
                .content("내용 수정")
                .user(user)
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .content("내용 수정 완료")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @MockUser
    @DisplayName("게시글 삭제")
    void test8() throws Exception  {
       User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글 존재하지 않는 게시글 조회")
    void test9() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @MockUser
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception  {
        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .content("내용 수정 완료")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}