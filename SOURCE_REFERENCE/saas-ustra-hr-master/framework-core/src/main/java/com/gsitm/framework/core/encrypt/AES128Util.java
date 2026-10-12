package com.gsitm.framework.core.encrypt;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * <pre>
 * com.gsitm.framework.core.encrypt
 *
 * </pre>
 * Description :
 *
 * @author : Mercury
 * @version : 1.0
 * @date : 2017-11-06
 */
@Slf4j
public class AES128Util {

    /**
     *
     * @param key
     * @param initVector
     * @param value
     * @return
     */
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (NullPointerException ex) {
        	log.error(ExceptionUtils.getMessage(ex));
    	} catch (Exception ex) {
        	log.error(ExceptionUtils.getMessage(ex));
        }

        return null;
    }

    /**
     *
     * @param key
     * @param initVector
     * @param encrypted
     * @return
     */
    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpecKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpecKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
        	log.error(ExceptionUtils.getMessage(ex));
        }

        return null;
    }
}
