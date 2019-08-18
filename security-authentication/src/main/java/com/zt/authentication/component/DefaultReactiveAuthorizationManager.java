package com.zt.authentication.component;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public class DefaultReactiveAuthorizationManager<ServerWebExchange> implements 
	ReactiveAuthorizationManager<ServerWebExchange> {

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, ServerWebExchange object) {
		// TODO Auto-generated method stub
		return null;
	}


}
