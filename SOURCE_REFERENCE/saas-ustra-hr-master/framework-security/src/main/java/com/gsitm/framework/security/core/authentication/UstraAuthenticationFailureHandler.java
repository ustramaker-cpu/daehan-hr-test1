package com.gsitm.framework.security.core.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * com.gsitm.framework.security.core.authentication
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/25
 */
public interface UstraAuthenticationFailureHandler {

    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException;
}
