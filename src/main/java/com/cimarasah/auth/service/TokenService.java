package com.cimarasah.auth.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;

public interface TokenService {

	String create(Device device);

	void refresh(HttpServletRequest request);

	void revoke(HttpServletRequest request);
}
