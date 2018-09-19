package com.cimarasah.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).pathMapping("/oauth/token", "/auth/token");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			        .withClient("integra_cli")
			        .secret("123456")
			        .authorizedGrantTypes("password")
			        .scopes("integra")
			     .and()
			        .withClient("domains-service")
			        .secret("testpassword")
			        .authorizedGrantTypes("refresh_token", "client_credentials")
			        .scopes("msapp")
			     .and()
			     	.withClient("PORTAL_CDC_APP_CLIENT")
			     	.secret("C&Lf^hktPYMVpuhVzQnXhvR7!AR^22XEAU8p3!4m")
			     	.authorizedGrantTypes("password")
			     	.scopes("portal");
	}
}