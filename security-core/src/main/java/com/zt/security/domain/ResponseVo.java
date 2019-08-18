package com.zt.security.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseVo<T> {
	
	private int code;
	
	private String msg;
	
	private T data;
	
}
