package com.hodolog.config;

import com.hodolog.exception.PostNotFound;
import com.hodolog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
public class TopPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var post  = postRepository.findById((Long) targetId).orElseThrow(PostNotFound::new);

       if(!post.getUserId().equals(userPrincipal.getUserId())){
           return false;
       }

        return true;
    }
}
