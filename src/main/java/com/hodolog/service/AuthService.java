package com.hodolog.service;

import com.hodolog.config.crypto.PasswordEncoder;
import com.hodolog.domain.User;
import com.hodolog.exception.AlreadyExistsEmailException;
import com.hodolog.repository.UserRepository;
import com.hodolog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if(userOptional.isPresent()){
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt(signup.getPassword());

        var user= User.builder()
              .name(signup.getName())
              .password(encryptedPassword)
              .email(signup.getEmail())
              .build();
        userRepository.save(user);
    }
}
