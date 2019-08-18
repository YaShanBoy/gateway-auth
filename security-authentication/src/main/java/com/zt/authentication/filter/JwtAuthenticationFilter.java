package com.zt.authentication.filter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements WebFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return null;
	}
	
}

/*
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
					throws IOException, ServletException {

		String token = request.getHeader("token");
		if (token==null) {
			throw new AuthenticationCredentialsNotFoundException("请先登陆，再访问！");
		}
		if(!token.startsWith("bearer ")) {
			throw new AuthenticationCredentialsNotFoundException("token无效！");
		}
		Jwt jwt = JwtHelper.decodeAndVerify(token, null);
		chain.doFilter(request, response);
	}
}
*/