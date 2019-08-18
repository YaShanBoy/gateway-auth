package com.zt.authorization.component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zt.security.domain.DefaultUserDetails;
import com.zt.security.domain.ResponseVo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component("defaultAuthenticationSuccessHandler")
@Slf4j
public class DefaultAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler,InitializingBean {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Value("serverpwd")
	private static String jksPassword="serverpwd";
	
	@Value("serverkey")
	private static String alias="serverkey";
	
	@Value("JKS")
	private static String keyStoreType = "JKS";
	
	private RsaSigner signer;
	
	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
		ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
		response.setStatusCode(HttpStatus.OK);
		DefaultUserDetails user = (DefaultUserDetails)authentication.getPrincipal();
		user.eraseCredentials();
		String userJson = "";
		String resp = "";
			try {
				userJson = objectMapper.writeValueAsString(user);
				Jwt jwt = JwtHelper.encode(userJson, signer);
				ResponseVo<String> responseVo = new ResponseVo<>();
				responseVo.setData(jwt.getEncoded());
				resp = objectMapper.writeValueAsString(responseVo);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				log.error("转json失败：{}", e);
				response.setStatusCode(HttpStatus.EXPECTATION_FAILED);
			}
			DataBuffer buffer = response.bufferFactory().wrap(resp.getBytes(Charset.forName("UTF-8")));
		return response.writeWith(Mono.just(buffer));
	}
	

	//keytool -genkeypair -alias serverkey -keystore server.jks -keyalg RSA -keysize 1024 -validity 3650
	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = null;
		try {
			is = DefaultAuthenticationSuccessHandler.class.getClassLoader()
					.getResourceAsStream("config/server.jks");
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(is, jksPassword.toCharArray());
			RSAPrivateKey privateKey = (RSAPrivateKey)keyStore.getKey(alias, jksPassword.toCharArray());
			signer = new RsaSigner(privateKey);
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		InputStream is = null;
		try {
			is = DefaultAuthenticationSuccessHandler.class.getClassLoader()
					.getResourceAsStream("config/server.jks");
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(is, "serverpwd".toCharArray());
			RSAPrivateKey privateKey = (RSAPrivateKey)keyStore.getKey("serverkey", "serverpwd".toCharArray());
			PublicKey publicKey = keyStore.getCertificate("serverkey").getPublicKey();
			System.out.println(Base64Utils.encodeToString(publicKey.getEncoded()));
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
