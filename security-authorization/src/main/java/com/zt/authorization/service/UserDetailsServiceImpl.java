package com.zt.authorization.service;

import java.util.Date;
import java.util.HashSet;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zt.security.domain.DefaultUserDetails;

import reactor.core.publisher.Mono;

@Component
public class UserDetailsServiceImpl implements ReactiveUserDetailsService{

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		DefaultUserDetails user = new DefaultUserDetails("haha", "$2a$10$OUoIgXAA9GM7.II3M0m5heNgEgYV77ns0hu7Yi7xjiprnMgNBym.S",new HashSet<>());
		Date now = new Date();
		Date validity = new Date(now.getTime()+2*60*60*1000);
		user.setValidity(validity);
		user.setAppId("damoxueyuan");
		return Mono.just(user);
	}
	
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("aizc"));
	}
	
}
