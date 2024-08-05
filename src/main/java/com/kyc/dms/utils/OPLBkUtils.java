package com.kyc.dms.utils;

import java.util.Collection;
import java.util.UUID;

public class OPLBkUtils {

	public static String ENROLLMENT_PROP_NAME = "OPL_BUCKET_STRG";
	public static String ENROLLMENT_BUCKET_NAME = "REGISTRY_REQ_RES_STORE_S3_BUCKET_NAME";
	
	
	public static boolean isObjectNullOrEmpty(Object value) {
		return (value == null
				|| (value instanceof String
						? (((String) value).isEmpty() || "".equals(((String) value).trim()) || "null".equals(value)
								|| "(null)".equals(value) || "undefined".equals(value))
						: false));
	}
	
	public static boolean isListNullOrEmpty(Collection<?> data) {
		return (data == null || data.isEmpty());
	}
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
