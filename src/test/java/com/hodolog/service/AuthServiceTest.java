package com.hodolog.service;


import com.hodolog.domain.User;
import com.hodolog.exception.AlreadyExistsEmailException;
import com.hodolog.repository.UserRepository;
import com.hodolog.request.Signup;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1(){
        Signup signup = Signup.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("test")
                .build();

        authService.signup(signup);

        Assertions.assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();

        Assertions.assertEquals("test@gmail.com", user.getEmail());
       Assertions.assertNotNull(user.getPassword());
       Assertions.assertNotEquals("1234", user.getPassword());
        Assertions.assertEquals("test", user.getName());
    }

    @Test
    @DisplayName("회원가입 시 중복된 이메일")
    void test2(){
        User user = User.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("test")
                .build();

        userRepository.save(user);

        Signup signup = Signup.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("test")
                .build();

        Assertions.assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }
}
