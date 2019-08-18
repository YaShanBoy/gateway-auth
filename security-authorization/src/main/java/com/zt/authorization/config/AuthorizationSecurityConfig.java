package com.zt.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;

@Configuration("authorizationSecurityConfig")
@EnableWebFluxSecurity
public class AuthorizationSecurityConfig {
	
	@Autowired
	private ServerAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		return http
				.authorizeExchange()
				.pathMatchers("/**")
				.authenticated()
				.and()
				.formLogin()
				.authenticationSuccessHandler(authenticationSuccessHandler)
				.and()
				.cors().disable()
				.csrf().disable()
				.build();
	}
}
/*
@EnableWebSecurity
public class AuthorizationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private LogoutHandler logoutHandler;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated() // 其他地址的访问均需验证权限
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.permitAll()
				.and()
				.addFilterAt(new MyUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.logout()
				.addLogoutHandler(logoutHandler);
	}
	
}
*/