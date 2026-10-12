package com.gsitm.framework.core.encrypt;

import java.security.MessageDigest;

/**
 * <pre>
 * com.gsitm.framework.core.encrypt
 *
 * </pre>
 * Description :
 *
 * @author : Mercury
 * @version : 1.0
 * @date : 2017-10-23
 */
public class SHA256Encrypt {

    /**
     *
     * @param value
     * @return
     */
    public static String encrypt(String value) {
        StringBuffer hexString = null;
        if (value == null) {
            return null;
        }
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes("UTF-8"));
            hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

        } catch(Exception ex){
            throw new RuntimeException(ex);
        }

        return hexString==null ? null : hexString.toString();
    }

}
