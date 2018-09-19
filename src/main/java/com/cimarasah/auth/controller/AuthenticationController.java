package com.cimarasah.auth.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cimarasah.auth.config.JwtSecurityProperties;
import com.cimarasah.auth.domain.request.JwtAuthenticationRequest;
import com.cimarasah.auth.domain.response.JwtAuthenticationResponse;
import com.cimarasah.auth.security.jwt.JwtFactory;
import com.cimarasah.auth.service.TokenService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	private JwtFactory jwtTokenFactory;
	private JwtSecurityProperties jwtSecurityProperties;
	private AuthenticationManager authenticationManager;
	private TokenService tokenService;
	
	@Inject
	public AuthenticationController(AuthenticationManager authenticationManager, JwtFactory jwtTokenFactory, JwtSecurityProperties jwtSecurityProperties, TokenService tokenService) {
		this.jwtTokenFactory = jwtTokenFactory;
		this.jwtSecurityProperties = jwtSecurityProperties;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@PostMapping(value = "/auth/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest, Device device) {
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok(new JwtAuthenticationResponse(tokenService.create(device), jwtTokenFactory.generateExpirationDate(), jwtSecurityProperties.getType()));
	}

	@PostMapping(value = "/auth/authorize", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		return;
	}

	@PostMapping(value = "/auth/token/revoke")
	@ResponseStatus(HttpStatus.OK)
	public void revokeToken(HttpServletRequest request, HttpServletResponse response) {
		tokenService.revoke(request);
	}

	@PostMapping(value = "/auth/token/refresh")
	@ResponseStatus(HttpStatus.OK)
	public void refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
	}
}