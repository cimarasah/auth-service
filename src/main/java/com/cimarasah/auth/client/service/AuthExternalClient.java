package com.cimarasah.auth.client.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cimarasah.auth.client.config.ServiceConfiguration;
import com.cimarasah.auth.client.model.AuthExternalRequest;
import com.cimarasah.auth.client.model.AuthExternalResponse;

@FeignClient(name = "${ek9.api.name}", url = "${ek9.api.uri}", configuration = ServiceConfiguration.class)
public interface AuthExternalClient {

	@RequestMapping(method = RequestMethod.POST, value = "/ek9-service/ek9/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	AuthExternalResponse auth(@RequestBody AuthExternalRequest authExternalRequest);
}
