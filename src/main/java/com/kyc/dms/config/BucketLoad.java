package com.kyc.dms.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.kyc.dms.model.BucketEnrlmntProp;
import com.kyc.dms.model.BucketListProxy;
import com.kyc.dms.model.BucketProxy;
import com.kyc.dms.utils.EncryptionUtils;
import com.kyc.dms.utils.OPLBkUtils;
import com.kyc.dms.utils.OPLJSONObjectHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BucketLoad {

	
	
	@Autowired
	private ApplicationContext applicationContext;
	/**
	 * CREATE MULTIPLE BEAN FROM ENROLLMENT PROPERTIES CONFIGURATION
	 * 
	 * @return
	 */
	@Bean
	public BucketListProxy getS3BucketList() {
		BucketListProxy bucketListProxy = null;
		try {

//			String decrypt = "[{\"bucketName\":\"prod-jansuraksha-data\",\"bucketPath\":\"request\",\"accessKey\":\"AKIAV6JCA72NOCAUP7BF\",\"secretKey\":\"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\",\"url\":\"https://prod-jansuraksha-data.s3.ap-south-1.amazonaws.com\",\"access\":\"private\",\"bucketType\":\"S3\"}]";

			/* LOAD CONFIGURATION FROM SYSTEM ENROLLMENT PROPERTIES */
			String configuration = System.getenv(OPLBkUtils.ENROLLMENT_PROP_NAME);
			if (!OPLBkUtils.isObjectNullOrEmpty(configuration)) {

				/* DECRYPT CONFIGURATION PROPERTIES USING ENCRYPTION UTILS */
				String decrypt = EncryptionUtils.decrypt(configuration);
				@SuppressWarnings("unchecked")
				List<BucketEnrlmntProp> bkList = OPLJSONObjectHelper.getListOfObjects(decrypt, null,
						BucketEnrlmntProp.class);
				if (bkList.size() > 0) {
					List<BucketProxy> bucketProxies = new ArrayList<>(bkList.size());
					BucketProxy bucketProxy = null;
					for (BucketEnrlmntProp properties : bkList) {
						bucketProxy = new BucketProxy(properties.getBucketName(), properties.getBucketCode(), properties.getBucketPath(),
								properties.getUrl(), amazons3(properties));
						bucketProxies.add(bucketProxy);
					}
					bucketListProxy = new BucketListProxy(bucketProxies);
					applicationContext.getAutowireCapableBeanFactory().autowireBean(bucketListProxy);
				}
			}
		} catch (Exception e) {
			log.error("Exception while load Bucket Beans - ", e);
			((ConfigurableApplicationContext) applicationContext).close();
		}
		return bucketListProxy;
	}

	
	/**
	 * PREPARE AmazonS3 OBJECT
	 * 
	 * @param bucket
	 * @return
	 */
	public AmazonS3 amazons3(BucketEnrlmntProp bucket) {
		if ("S3DEFAULTCLIENT".equalsIgnoreCase(bucket.getBucketType())) {
			return AmazonS3ClientBuilder.defaultClient();
		}
		log.error("{bucketType:S3DEFAULTCLIENT} is not set in server Environment");
		return null;
//		return AmazonS3ClientBuilder.standard()
//				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials(bucket)))
//				.withRegion(Regions.AP_SOUTH_1).build();
	}
	
	
}
