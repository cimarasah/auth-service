package com.cimarasah.auth.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

import com.cimarasah.auth.client.service.AuthExternalClient;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	@Inject
	private AuthExternalClient authExternalClient;

	public CustomAuthenticationProvider() {
		super();
	}



	private boolean validateLegacyPassword(String currentPassword, String presentedPassword) {
		return currentPassword.equals(legacyToSHA1(presentedPassword));
	}

	private String legacyToSHA1(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");

			md.update(value.getBytes());

			BigInteger hash = new BigInteger(1, md.digest());

			return hash.toString(16);
		} catch (NoSuchAlgorithmException exception) {
		}
		return "";
	}
}
