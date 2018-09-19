package com.cimarasah.auth.client.builder;

import com.cimarasah.auth.client.model.AuthExternalRequest;


public class AuthExternalRequestBuilder {

	private String username;
	private String password;

	public static AuthExternalRequestBuilder builder() {
		return new AuthExternalRequestBuilder();
	}

	public AuthExternalRequestBuilder withUsername(String value) {
		this.username = value;
		return this;
	}

	public AuthExternalRequestBuilder withPassword(String value) {
		this.password = value;
		return this;
	}

	public AuthExternalRequest build() {
		AuthExternalRequest request = new AuthExternalRequest();
		request.setUsername(username);
		request.setPassword(password);
		return request;
	}

}
