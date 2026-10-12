package com.gsitm.framework.core.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * com.gsitm.framework.core.utils
 *
 * Description : JWT 서명 키 생성 유틸
 *
 * jjwt 0.10 이상은 JWA(RFC 7518) 규격에 따라 HS256 서명 키에 256bit 이상을 요구한다.
 * 설정 파일의 시크릿 문자열은 그보다 짧으므로 SHA-256 으로 파생시켜 256bit 키를 만든다.
 * 토큰 생성/검증 양쪽 모두 이 클래스를 통해 키를 얻어야 서명이 일치한다.
 * </pre>
 */
public final class JwtSecretKeys {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private JwtSecretKeys() {
    }

    /**
     * 설정 시크릿 문자열로부터 HS256 서명 키를 만든다.
     *
     * @param secret 설정 파일에 지정된 시크릿 문자열
     * @return 256bit HMAC-SHA256 키
     */
    public static SecretKey hs256(String secret) {
        if (secret == null) {
            throw new IllegalArgumentException("jwt secret key must not be null");
        }

        try {
            byte[] derived = MessageDigest.getInstance("SHA-256")
                    .digest(secret.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(derived, HMAC_SHA256);
        } catch (NoSuchAlgorithmException e) { // SHA-256 은 모든 JDK 가 제공한다
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }
}
