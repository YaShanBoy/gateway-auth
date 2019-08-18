package com.zt.authorization.filter;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class MyUsernamePasswordAuthenticationFilter implements WebFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		MediaType contentType = request.getHeaders().getContentType();
		if(contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
			Flux<String> flux = request.getBody().map(data->data.toString());
			
		}
		return null;
	}
	
}

/**
@Slf4j
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || 
				request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			ServletInputStream is = null;
			UsernameAndPasswordVo usernameAndPasswordVo = new UsernameAndPasswordVo();
			try {
				is = request.getInputStream();
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				usernameAndPasswordVo = objectMapper.readValue(is, UsernameAndPasswordVo.class);
			} catch (IOException e) {
				log.error("登录失败:{}", e);
			} finally {
				if(is!=null) {
					try {
						is.close();
					} catch (IOException e) {
						log.error("登录失败后,流也关闭失败：{}", e);
					}
				}
			}
			String username = usernameAndPasswordVo.getUsername();
			String password = usernameAndPasswordVo.getPassword();

			if (username == null) {
				username = "";
			}

			if (password == null) {
				password = "";
			}

			username = username.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			setDetails(request, authRequest);

			return this.getAuthenticationManager().authenticate(authRequest);
		}else {
			return super.attemptAuthentication(request, response);
		}
	}
	
}
*/