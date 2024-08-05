package com.kyc.dms.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionUtils {
	private static final String ALGORITHM = "AES";
	private static final String KEY = "C@p!tta@W0rld#AES";
	public static final String MD5 = "MD5";
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(EncryptionUtils.class);
	/*
	 * ENCRYPT PLAIN TEXT
	 * 
	 * @param plainText
	 * @return
	 */
	public static String encrypt(String plainText) {
		try {
			if (!OPLBkUtils.isObjectNullOrEmpty(plainText)) {
				byte[] keyBytes = Arrays.copyOf(KEY.getBytes("ASCII"), 16);

				SecretKey key = new SecretKeySpec(keyBytes, ALGORITHM);
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, key);

				byte[] cleartext = plainText.getBytes(StandardCharsets.UTF_8);
				byte[] ciphertextBytes = cipher.doFinal(cleartext);

				return new String(Hex.encodeHex(ciphertextBytes));
			}
		} catch (Exception e) {
			logger.error("Error while encrypting data : " + plainText, e);
		}
		return null;
	}
	
	/**
	 * DECRYPT TEXT
	 * 
	 * @param encryptedText
	 * @return
	 */
	public static String decrypt(String encryptedText) {
		try {
			if (!OPLBkUtils.isObjectNullOrEmpty(encryptedText)) {
				byte[] keyBytes = Arrays.copyOf(KEY.getBytes("ASCII"), 16);
				SecretKey key = new SecretKeySpec(keyBytes, ALGORITHM);
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, key);
				return new String(cipher.doFinal(Hex.decodeHex(encryptedText.toCharArray())));
			}
		} catch (Exception e) {
			logger.error("Error while decrypting data : " + encryptedText, e);
		}
		return null;
	}
}
