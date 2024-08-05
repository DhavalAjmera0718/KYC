package com.kyc.dms.model;

import com.amazonaws.services.s3.AmazonS3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketProxy {

	private String bucketName;
	private String bucketCode;
	private String bucketPath;
	private String url;
	private AmazonS3 amazonS3;

}
