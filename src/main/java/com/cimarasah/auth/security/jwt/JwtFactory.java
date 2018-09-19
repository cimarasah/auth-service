package com.cimarasah.auth.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;

import com.cimarasah.auth.domain.model.User;

public interface JwtFactory {

    String getUsernameFromToken(String token);

    String generateToken(User user, Device device);

    Boolean canTokenBeRefreshed(String token);

    Date generateInactiveDate();

    Date generateExpirationDate();

    String getToken(HttpServletRequest request);
}
