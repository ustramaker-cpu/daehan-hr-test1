package com.gsitm.framework.security.core.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * com.gsitm.framework.security.core.authentication
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/16
 */
public interface UstraPasswordEncoder {

    PasswordEncoder passwordEncoder();
}
