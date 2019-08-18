package com.zt.authorization.component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component("defaultAuthenticationFailureHandler")
public class DefaultAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
		return null;
	}

}
