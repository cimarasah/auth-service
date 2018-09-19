package com.cimarasah.auth.domain;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

import com.cimarasah.auth.domain.model.User;

@Component
public class DefaultUserDetailsChecker implements UserDetailsChecker {

    @Override
    public void check(UserDetails userDetails) {
        User user = (User) userDetails;

        
    }
}