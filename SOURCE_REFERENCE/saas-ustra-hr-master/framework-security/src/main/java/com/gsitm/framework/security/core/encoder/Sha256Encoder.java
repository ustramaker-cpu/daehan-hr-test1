package com.gsitm.framework.security.core.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Encoder implements PasswordEncoder {
    String saltkey;

    public Sha256Encoder(String saltkey){
		this.saltkey = saltkey;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			String result = getEncryptBySalt(rawPassword.toString(), saltkey);

			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		boolean result = false;
		if(encodedPassword.equals(this.encode(rawPassword))) {
			result = true;
		}
		return result;
	}

	public String getEncryptBySalt(String source, String saltkey) {
		byte[] salt = makeSalt(saltkey).getBytes();
		String result = "";

		byte[] a = source.getBytes();
		byte[] bytes = new byte[a.length + salt.length];

		System.arraycopy(a, 0, bytes, 0, a.length);
		System.arraycopy(salt, 0, bytes, a.length, salt.length);

		try {
			// 암호화 방식 지정 메소드
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);

			byte[] byteData = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			}

			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return result;
	}

	public String makeSalt(String saltkey) {
        byte[] salt = saltkey.getBytes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < salt.length; i++) {
            // byte 값을 Hex 값으로 바꾸기.
            sb.append(String.format("%02x",salt[i]));
        }

        return sb.toString();
    }
}