package com.gsitm.framework.security.jwt.claims;

import org.springframework.security.core.Authentication;

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
public interface DefaultJwtClaimProvider {

    void append(JwtTokenClaims claims, Authentication authentication);
}
