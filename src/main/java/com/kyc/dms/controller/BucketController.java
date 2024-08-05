package com.kyc.dms.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kyc.dms.utils.BucketStorageUtils;
import com.kyc.dms.utils.TestObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BucketController {

	
	
	@Autowired
	private BucketStorageUtils bucketStorageUtils;
	
	/**
	 * ONLY FOR TESTING PURPOSE
	 * 
	 * @param bucketName
	 * @return
	 */
	@GetMapping(value = "/opl/bucket/seriablize/{bucketName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadedReqResFile(@PathVariable("bucketName") String bucketName) {
		TestObject test = new TestObject().toBuilder().id(1).message("Success").status(200).build();
		String randomName = UUID.randomUUID().toString();
		bucketStorageUtils.upload(bucketName, test, randomName);
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
	
	
}
