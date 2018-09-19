package com.cimarasah.auth.domain.repository;

import java.util.Date;

public interface TokenRepository {

	Date findValidationDate(String token, String username);

	String findToken(String username);

	void delete(String token, String username);

	void save(String jwt, String username, Date validationDate);

	void refreshValidationDate(String username, Date validationDate);
}
