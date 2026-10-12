package com.gsitm.framework.security.jwt.config;

import com.gsitm.framework.security.core.authentication.UstraAuthenticationSuccessHandler;
import com.gsitm.framework.security.core.properties.LoginProperties;
import com.gsitm.framework.security.jwt.properties.JwtProperties;
import com.gsitm.framework.security.jwt.storage.DefaultJwtStorage;
import com.gsitm.framework.web.properties.ExceptionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class DefaultJwtAuthenticationSuccessHandler implements UstraAuthenticationSuccessHandler {
	private final UstraJwtTokenConfig ustraJwtTokenConfig;
	private final RequestCache requestCache;
	private final JwtProperties jwtProperties;
	private final LoginProperties loginProperties;
	private final ExceptionProperties exceptionProperties;
	private final DefaultJwtStorage defaultJwtStorage;

	/**
	 * 로그인 성공시, 토큰을 발급하여 쿠키에 httpOnly속성으로 셋팅
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String successUri) throws IOException, ServletException {
		String targetUrl = successUri;
		String token = ustraJwtTokenConfig.createToken(authentication);
		defaultJwtStorage.store(response, jwtProperties.getTokenKey(), token);
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(int i=0;i<cookies.length;i++){
				if("JSESSIONID".equals(cookies[i].getName())){
					Cookie cookie = new Cookie("JSESSIONID", null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					request.getSession().invalidate();
					break;
				}
			}
		}

		if(savedRequest!=null && (savedRequest.getRedirectUrl().indexOf(loginProperties.getDefaultUri()) == -1 && savedRequest.getRedirectUrl().indexOf(exceptionProperties.getErrorUri()) < 0)){ //에러페이지에서 올경우, 메인페이지로 로그인 시킨다.
			targetUrl = savedRequest.getRedirectUrl();
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}else {
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}
	}


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication, String successUri) throws IOException, ServletException {

	}
}