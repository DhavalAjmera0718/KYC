package com.kyc.dms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kyc.dms.model.BucketEnrlmntProp;



public class GenerateEnrollmentString {

	
	public static void main(String[] args) {
//		SpringApplication.run(KycDmsApplication.class, args);
		Scanner s = new Scanner(System.in);
		System.out.println("ENTER THE TOTAL AMOUNT OF BUCKET:");
		int totalBucket = s.nextInt();

		List<BucketEnrlmntProp> properties = new ArrayList<>(totalBucket);
		BucketEnrlmntProp prop = null;
		for (int i = 0; i < totalBucket; i++) {
			int seqNo = i + 1;
			prop = new BucketEnrlmntProp();

			System.out.println("BUCKET" + seqNo + " NAME:");
			prop.setBucketName(s.next());
			
			System.out.println("BUCKET" + seqNo + " CODE:");
			prop.setBucketCode(s.next());

			System.out.println("BUCKET" + seqNo + " TYPE (S3|S3DEFAULTCLIENT):");
			prop.setBucketType(s.next());

			System.out.println("BUCKET" + seqNo + " PATH:");
			prop.setBucketPath(s.next());

			System.out.println("BUCKET" + seqNo + " ACCESSKEY:");
			prop.setAccessKey(s.next());

			System.out.println("BUCKET" + seqNo + " SECRETKEY:");
			prop.setSecretKey(s.next());

			System.out.println("BUCKET" + seqNo + " URL:");
			prop.setUrl(s.next());

			System.out.println("BUCKET" + seqNo + " ACCESS (Private/Public):");
			prop.setAccess(s.next());
			properties.add(prop);

			System.out.println("\n\n");
		}
		try {
			System.out.println("GENERATING STRING...............");
			String generateString = OPLJSONObjectHelper.getStringfromListOfObject(properties);
			System.out.println("PLAN STRING FOR VERIFICATION:- " + generateString);
			System.out.println("ARE YOU SURE YOU WANT GENERATE ENCRYPTED STRING VALUE ?(Y/N)");
			String result = s.next();
			if ("Y".equalsIgnoreCase(result)) {
				System.out.println("GENERATING ENCRYPT STRING...............");
				String encrypt = EncryptionUtils.encrypt(generateString);
				System.out.println("ENROLLMENT PROPERTY KEY:- " + OPLBkUtils.ENROLLMENT_PROP_NAME);
				System.out.println("ENROLLMENT PROPERTY VALUE:- " + encrypt);
			} else {
				System.out.println("Thank you............ ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
	}	
	
	
}
