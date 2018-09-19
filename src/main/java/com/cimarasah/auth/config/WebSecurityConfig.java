package com.cimarasah.auth.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cimarasah.auth.JwtAuthenticationFilter;
import com.cimarasah.auth.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userService;
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	

	@Inject
	public WebSecurityConfig(UserDetailsService userService, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
		this.userService = userService;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
	    provider.setPasswordEncoder(new ShaPasswordEncoder());
	    provider.setUserDetailsService(userService);
	    return provider;
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.anyRequest().permitAll().and().cors().disable();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
