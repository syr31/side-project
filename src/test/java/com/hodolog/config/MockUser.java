package com.hodolog.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContext.class)
public @interface MockUser {
    String email() default "test";
    String name() default  "test@gmail.com";
    String password() default  "1234";
//    String role() default "ROLE_ADMIN";
}
