package com.zt.security.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.ToString;


@ToString
public class DefaultUserDetails extends User{

	private static final long serialVersionUID = 1L;
	
	public DefaultUserDetails() {
		super("anonymous", "", new HashSet<>());
	}

	public DefaultUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	private String jti = UUID.randomUUID().toString();
	
	private String appId;
	
	private String appName;
	
	private Date validity;
	
	private Map<String,Object> otherInfo;

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public Map<String, Object> getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(Map<String, Object> otherInfo) {
		this.otherInfo = otherInfo;
	}
	
}
