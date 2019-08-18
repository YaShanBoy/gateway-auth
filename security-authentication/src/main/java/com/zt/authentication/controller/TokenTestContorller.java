package com.zt.authentication.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zt.security.domain.DefaultUserDetails;
import com.zt.security.domain.ResponseVo;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("token")
public class TokenTestContorller {
	
	@RequestMapping("test")
	public Mono<ResponseVo<?>> tokenTest(){
		ResponseVo<DefaultUserDetails> resp = new ResponseVo<>();
		DefaultUserDetails user =(DefaultUserDetails)SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		resp.setData(user);
		return Mono.just(resp);
	}
	
}
