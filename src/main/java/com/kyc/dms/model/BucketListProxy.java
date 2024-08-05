package com.kyc.dms.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketListProxy {

	private List<BucketProxy> bucketProxies;

	public BucketProxy filter(String bucketName) {
		Optional<BucketProxy> srBucketPrx = bucketProxies.stream().filter(a -> Objects.equals(a.getBucketName(), bucketName) || Objects.equals(a.getBucketCode(), bucketName))
				.findFirst();
		return srBucketPrx.orElse(null);
	}

}

