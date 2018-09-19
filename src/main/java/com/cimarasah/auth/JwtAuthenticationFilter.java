package com.cimarasah.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cimarasah.auth.controller.ErrorMessage;
import com.cimarasah.auth.domain.repository.TokenRepository;
import com.cimarasah.auth.security.jwt.JwtFactory;
import com.cimarasah.auth.service.TokenService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private JwtFactory jwtTokenFactory;

	@Inject
	private UserDetailsService userDetailsService;

	@Inject
	private TokenRepository tokenRepository;
	
	@Inject
	private TokenService tokenService;

	public static final String AUTH_MATCHER = "/auth/token";
	public static final String AUTHORIZE_MATCHER = "/auth/authorize";
	public static final String REFRESH_MATCHER = "/refresh";

	private List<String> pathsToSkip = Arrays.asList(AUTH_MATCHER, REFRESH_MATCHER);
	private List<String> pathAuthorize = Collections.singletonList(AUTHORIZE_MATCHER);



	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!skipPathRequest(request)) {
			String authToken = jwtTokenFactory.getToken(request);
			
			if (authToken != null) {
				try {
					String username = jwtTokenFactory.getUsernameFromToken(authToken);
					Date validationDate = tokenRepository.findValidationDate(authToken, username);

					if (validate(validationDate)) {
						if(!skipAuthorizeRequest(request)) {
							UserDetails principal = userDetailsService.loadUserByUsername(username);
							TokenBasedAuthentication authentication = new TokenBasedAuthentication(principal);
							authentication.setToken(authToken);
							SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                        tokenService.refresh(request);
                    } else {
						notAcceptable(response);
						return;
					}
				} catch (Exception ex) {
					notAcceptable(response);
					return;
				}
			} else {
				unauthorized(response);
				return;
			}
		}

		chain.doFilter(request, response);
	}
	
	private void unauthorized(HttpServletResponse response) throws IOException {
        setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autenticado. Não foi possível processar a solicitação");
	}
	
	private void notAcceptable(HttpServletResponse response) throws IOException {
	    setResponse(response, HttpServletResponse.SC_NOT_ACCEPTABLE, "Usuário não autenticado. Não foi possível processar a solicitação");
	}

	private void setResponse(HttpServletResponse response, int status, String message) throws IOException {
	    logger.error("{} {}", "Erro ao autenticar o usuário", message);

        ErrorMessage errorMessage = new ErrorMessage(status, message);
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(convertObjectToJson(errorMessage));
    }

	private boolean validate(Date validationDate) {
		return !validationDate.before(new Date());
	}

	private boolean skipPathRequest(HttpServletRequest request) {
		List<RequestMatcher> match = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
		OrRequestMatcher matchers = new OrRequestMatcher(match);
		return matchers.matches(request);
	}

	private boolean skipAuthorizeRequest(HttpServletRequest request) {
		List<RequestMatcher> match = pathAuthorize.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
		OrRequestMatcher matchers = new OrRequestMatcher(match);
		return matchers.matches(request);
	}

    private String convertObjectToJson(Object object) throws IOException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}