package com.cimarasah.auth.security.jwt;

import com.cimarasah.auth.domain.model.User;

public class UserInfoFactory {

    public static UserInfo create(User user) {


        return UserInfoBuilder.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userName(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
