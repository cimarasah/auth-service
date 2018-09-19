package com.cimarasah.auth.domain.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtAuthenticationResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private Date expiresIn;

	public JwtAuthenticationResponse(String accessToken, Date expiresIn, String tokenType) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}
}