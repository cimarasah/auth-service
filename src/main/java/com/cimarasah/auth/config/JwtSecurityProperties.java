package com.cimarasah.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtSecurityProperties {

	private String secret;
	private int expiresInHours;
	private int inactiveInMinutes;
	private String header;
	private String name;
	private String type;
	private String keyStorePassword;
	private String privateKeyPassword;
	private String alias;

	public String getSecret() {
		return secret;
	}

	public int getExpiresInHours() {
		return expiresInHours;
	}

	public int getInactiveInMinutes() {
		return inactiveInMinutes;
	}

	public String getHeader() {
		return header;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setExpiresInHours(int expiresInHours) {
		this.expiresInHours = expiresInHours;
	}

	public void setInactiveInMinutes(int inactiveInMinutes) {
		this.inactiveInMinutes = inactiveInMinutes;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getPrivateKeyPassword() {
		return privateKeyPassword;
	}

	public void setPrivateKeyPassword(String privateKeyPassword) {
		this.privateKeyPassword = privateKeyPassword;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
