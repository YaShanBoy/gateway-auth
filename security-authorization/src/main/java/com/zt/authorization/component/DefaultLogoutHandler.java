package com.zt.authorization.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component("defaultLogoutHandler")
public class DefaultLogoutHandler implements ServerLogoutHandler {

	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		return null;
	}

}
