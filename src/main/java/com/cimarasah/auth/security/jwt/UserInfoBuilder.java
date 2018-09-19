package com.cimarasah.auth.security.jwt;

import java.util.List;



public final class UserInfoBuilder {
    private Long userId;
	private String name;
	private String email;
	private String userName;
	private String password;

    private UserInfoBuilder() {
    }

    public static UserInfoBuilder builder() {
        return new UserInfoBuilder();
    }

    public UserInfoBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }
    
    
    public UserInfoBuilder name(String name) {
        this.name = name;
        return this;
    }
    public UserInfoBuilder password(String password) {
        this.password = password;
        return this;
    }
    
    public UserInfoBuilder email(String email) {
        this.email = email;
        return this;
    }
    
    public UserInfoBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }
    
	public UserInfo build() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setName(name);;
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setUsername(userName);
        
        return userInfo;
    }
}
