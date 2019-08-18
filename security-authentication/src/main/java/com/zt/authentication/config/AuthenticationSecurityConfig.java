package com.zt.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration("authenticationSecurityConfig")
@EnableWebFluxSecurity
public class AuthenticationSecurityConfig {
	
	@Autowired
	private ServerSecurityContextRepository securityContextRepository;
	
	@Autowired
	private ReactiveAuthenticationManager authenticationManager;
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
				.authorizeExchange()
				.pathMatchers("/**")
				.authenticated()
				.and()
				.securityContextRepository(securityContextRepository)
				//.authenticationManager(authenticationManager)
				.cors().disable()
				.csrf().disable()
				.build();
	}
	
}

/*
public class AuthenticationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated() // 其他地址的访问均需验证权限
				.and()
				.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), 
						UsernamePasswordAuthenticationFilter.class);
	}
	
}
*/