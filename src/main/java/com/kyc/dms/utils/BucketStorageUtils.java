package com.kyc.dms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kyc.dms.domain.DOCStorage;
import com.kyc.dms.model.BucketListProxy;
import com.kyc.dms.model.BucketProxy;
import com.kyc.dms.repository.DOCStorageRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BucketStorageUtils {

	@Autowired
	private BucketListProxy bucketListProxy;
	
	
	@Autowired
	private DOCStorageRepository docStorageRepository;
	
	/**
	 * UPLOAD CLASS OBJECT FILE IN BUCKET
	 *
	 * @param bucketName
	 * @param object
	 * @param docRefrenceId (Optional)
	 * @return
	 */
	public String upload(String bucketName, Object object, String docRefrenceId) {
		DOCStorage docStorage = new DOCStorage(docRefrenceId, false);
		try {
			if (OPLBkUtils.isObjectNullOrEmpty(docRefrenceId)) {
				docRefrenceId = OPLBkUtils.generateUUID();
				docStorage.setReferenceId(docRefrenceId);
			}
			BucketProxy s3Bucket = validate(bucketName, docStorage);
			if (s3Bucket == null) {
				return null;
			}
			File file = new File(docRefrenceId + ".txt");
			try {
				ObjectOutputStream objectOS = new ObjectOutputStream(new FileOutputStream(file));
				objectOS.writeObject(object);
				objectOS.close();
			} catch (Exception e) {
				log.error("EXCEPTION WHILE OBJECT SERIALIZATION -", e);
				docStorage.setMessage("EXCEPTION WHILE FILE WRITE: " + e.getMessage());
				return null;
			}
			docStorage.setFileSize(file.getTotalSpace());
			docStorage.setFileType(FilenameUtils.getExtension(file.getName()));
			docStorage.setOriginalName(file.getName());
			boolean uploadFile = uploadFile(file, docRefrenceId, s3Bucket, docStorage, file.getName());
			if(uploadFile) {
				return docRefrenceId;	
			}
		} catch (Exception e) {
			log.error("EXCEPTION WHILE UPLOAD OBJECT IN BUCKET ---->", e);
			docStorage.setMessage(e.getMessage());
		} finally {
			if(!docStorage.getIsActive()) {
				docStorage.setModifiedDate(new Date());
				docStorageRepository.save(docStorage);	
			}
		}
		return null;
	}

	/**
	 * FILTER BUCKET AND VALIDATE
	 *
	 * @param bucketName
	 * @return
	 */
	private BucketProxy validate(String bucketName, DOCStorage docStorage) {
		BucketProxy s3Bucket = bucketListProxy.filter(bucketName);
		if (s3Bucket == null) {
			log.error("NO BUCKET FOUND FROM BUCKET NAME --->" + bucketName);
			docStorage.setMessage("NO BUCKET FOUND FROM BUCKET NAME --->" + bucketName);
			return null;
		}
		boolean doesBucketExist = s3Bucket.getAmazonS3()
				.doesBucketExist(s3Bucket.getBucketName() + "/" + s3Bucket.getBucketPath());
		if (!doesBucketExist) {
			log.warn("BUCKET PATH NOT AVAILABLE -" + s3Bucket.getBucketName() + "/" + s3Bucket.getBucketPath());
			docStorage.setMessage("BUCKET PATH NOT AVAILABLE -" + s3Bucket.getBucketName() + "/" + s3Bucket.getBucketPath());
			return null;
		}
		return s3Bucket;
	}
/****************************************************************************************************************************/	
	private boolean uploadFile(File file, String referenceId, BucketProxy bucket, DOCStorage docStorage, String uploadFileName) {
		try {
			// SET FILE DETAILS IN AUDIT TABLE
			docStorage.setFileType(FilenameUtils.getExtension(file.getName()));
			docStorage.setOriginalName(file.getName());
			docStorage.setFileSize(file.getTotalSpace());
			//log.info("docStorage ---> {}", docStorage);
			// UPLOAD FILE IN S3 BUCKET
			bucket.getAmazonS3().putObject(
					new PutObjectRequest(bucket.getBucketName(), bucket.getBucketPath()+"/"+uploadFileName, file));
			// SET BUCKET URL
			String url = bucket.getUrl().concat(bucket.getBucketPath()).concat("/").concat(uploadFileName);
			//log.info("url ---> {}", url);
			//https://development-hsbc-data.s3.ap-south-1.amazonaws.comdev-hsbc/UPLOAD-61b3c9bf-2a41-4f56-8d42-5b1f0ba4a35d.txt

			docStorage.setDocUrl(url);
			docStorage.setMessage("SUCCESS");
			docStorage.setIsActive(true);
			return true;
		} catch (Exception e) {
			log.error("EXCEPTION WHILE UPLOADING FILE ---> {}", e);
			docStorage.setMessage("EXCEPTION WHILE UPLOAD FILE: " + e.getMessage());
//			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
		return false;
	}
}
