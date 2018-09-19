package com.cimarasah.auth.service.impl;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cimarasah.auth.domain.model.User;
import com.cimarasah.auth.domain.repository.TokenRepository;
import com.cimarasah.auth.security.jwt.JwtFactory;
import com.cimarasah.auth.service.TokenService;

@Component
public class JwtTokenService implements TokenService {

	private TokenRepository tokenRepository;
	private JwtFactory jwtTokenFactory;
	
	@Inject
	public JwtTokenService(TokenRepository tokenRepository, JwtFactory jwtTokenFactory) {
		this.tokenRepository = tokenRepository;
		this.jwtTokenFactory = jwtTokenFactory;
	}
	
	@Override
	public String create(Device device) {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String jwt = tokenRepository.findToken(principal.getUsername());
        if(StringUtils.isEmpty(jwt)) {
            return createNewToken(device, principal);
        } else {
            Date validationDate = tokenRepository.findValidationDate(jwt, principal.getUsername());
            if (!validate(validationDate)) {
                return createNewToken(device, principal);
            }
        }
        return jwt;
	}

    private String createNewToken(Device device, User principal) {
        String jwt = jwtTokenFactory.generateToken(principal, device);
        tokenRepository.save(jwt, principal.getUsername(), jwtTokenFactory.generateInactiveDate());
        return jwt;
    }

    private boolean validate(Date validationDate) {
        return !validationDate.before(new Date());
    }
	
	@Override
	public void refresh(HttpServletRequest request) {
		String authToken = jwtTokenFactory.getToken(request);
		
		if (authToken != null && jwtTokenFactory.canTokenBeRefreshed(authToken)) {
			tokenRepository.refreshValidationDate(authToken, jwtTokenFactory.generateInactiveDate());
		}
	}
	
	@Override
	public void revoke(HttpServletRequest request) {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		tokenRepository.delete(jwtTokenFactory.getToken(request), principal.getUsername());
	}
}
