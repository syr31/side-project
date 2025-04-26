package com.hodolog.service;

import com.hodolog.domain.Post;
import com.hodolog.exception.PostNotFound;
import com.hodolog.repository.PostRepository;
import com.hodolog.request.PostCreate;
import com.hodolog.request.PostEdit;
import com.hodolog.request.PostSearch;
import com.hodolog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hodolog.domain.QPost.post;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.write(postCreate);

        Assertions.assertEquals(1l, postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        PostResponse response = postService.get(requestPost.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals("foo", response.getTitle());
        Assertions.assertEquals("bar", response.getContent());

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){
        List<Post> requestPosts = IntStream.range(0,20)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("제목 " + i)
                            .content("내용 " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        List<PostResponse> posts = postService.getList(postSearch);

        Assertions.assertEquals(10L, posts.size());
        Assertions.assertEquals("제목 19", posts.get(0).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        Post post = Post.builder()
                            .title("제목 수정")
                            .content("내용 수정")
                            .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정 완료")
                .content("내용 수정")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        Assertions.assertEquals("제목 수정 완료", changePost.getTitle());
        Assertions.assertEquals("내용 수정", changePost.getContent());

    }

    @Test
    @DisplayName("글 내용 수정")
    void test5(){
        Post post = Post.builder()
                .title("제목 수정")
                .content("내용 수정")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .content("내용 수정 완료")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        Assertions.assertEquals("제목 수정", changePost.getTitle());
        Assertions.assertEquals("내용 수정 완료", changePost.getContent());

    }
    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        Post post = Post.builder()
                .title("제목 수정")
                .content("내용 수정")
                .build();

        postRepository.save(post);

        postService.delete(post.getId());

        Assertions.assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7(){
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);


        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        Post post = Post.builder()
                .title("제목 수정")
                .content("내용 수정")
                .build();

        postRepository.save(post);

        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9(){
        Post post = Post.builder()
                .title("제목 수정")
                .content("내용 수정")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .content("내용 수정 완료")
                .build();

        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1, postEdit);
        });

    }


}