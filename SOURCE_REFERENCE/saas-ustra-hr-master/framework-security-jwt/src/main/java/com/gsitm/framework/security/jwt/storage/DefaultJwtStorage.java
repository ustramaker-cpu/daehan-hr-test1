package com.gsitm.framework.security.jwt.storage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>
 * com.gsitm.framework.security.jwt.config
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/14
 */
public interface DefaultJwtStorage {

    void store(HttpServletResponse httpServletResponse, String tokenKey, String key);

    String getToken(HttpServletRequest httpServletRequest, String tokenKey);
}
