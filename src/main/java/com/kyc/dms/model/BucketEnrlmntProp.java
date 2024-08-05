package com.kyc.dms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LIST OF PROPERTIES CONFIGURE IN ENROLLMENT VARIABLES
 * 
 * @author dhaval.ajmera
 * @date 31-july-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketEnrlmntProp {

	private String bucketName;
	private String bucketCode;
	private String bucketPath;
	private String accessKey;
	private String secretKey;
	private String url;
	private String access;
	private String bucketType;
}