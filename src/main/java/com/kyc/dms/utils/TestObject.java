package com.kyc.dms.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 98989898956L;
	private Integer id;
	private Integer status;
	private String message;
}