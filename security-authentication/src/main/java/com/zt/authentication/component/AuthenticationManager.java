package com.zt.authentication.component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zt.security.domain.DefaultUserDetails;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager,InitializingBean{
	
	@Value("${security.publicKey}")
	private String publicKeyStr;
	
	private SignatureVerifier verifier;
	
	@Value("${security.alg:RSA}")
	private String alg;
	
	private static ObjectMapper ojbectMapper = new ObjectMapper();
	
	static {
		ojbectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = (String)authentication.getCredentials();
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
			String claims = jwt.getClaims();
			DefaultUserDetails user = ojbectMapper.readValue(claims, DefaultUserDetails.class);
			authentication = new UsernamePasswordAuthenticationToken(user, null, 
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch(Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("口令非法!");
		}
		return Mono.just(authentication);
	}

	@Override
	public void afterPropertiesSet() throws InvalidKeySpecException, NoSuchAlgorithmException {
		System.out.println(publicKeyStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Utils.decodeFromString(publicKeyStr));
		RSAPublicKey publicKey;
		publicKey = (RSAPublicKey)KeyFactory.getInstance(alg).generatePublic(keySpec);
		verifier = new RsaVerifier(publicKey);
	}
	
	
}
