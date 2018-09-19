package com.cimarasah.auth.client.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class ServiceConfiguration {

	@Bean
	public Encoder feignEncoder() {
		return new JacksonEncoder(getModules());
	}

	@Bean
	public Decoder feignDecoder() {
		return new JacksonDecoder(getModules());
	}

	private Iterable<Module> getModules() {
		return Arrays.asList(new JavaTimeModule());
	}
}