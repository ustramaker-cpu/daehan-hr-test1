package com.gsitm.framework.security.core.authority;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface UstraDynamicAuthorityChecker {

    boolean check(HttpServletRequest request, Authentication authentication);
}
