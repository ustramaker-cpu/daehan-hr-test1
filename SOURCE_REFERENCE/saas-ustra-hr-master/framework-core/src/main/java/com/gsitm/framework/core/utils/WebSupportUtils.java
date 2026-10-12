package com.gsitm.framework.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class WebSupportUtils {

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

    public static String getUserAgent(String userAgent){
        String browser = "";
        if (StringUtils.isEmpty(userAgent)){
            return null;
        }
        if (userAgent.indexOf("Edge") > -1) {
            browser = "IE Edge";
        } else if (userAgent.indexOf("Trident") > -1 || userAgent.indexOf("MSIE") > -1) {
            browser = "IE";
            String version = null;
            version = userAgent.substring(userAgent.indexOf("Trident")+8, userAgent.indexOf("Trident")+11);
            if (version.equals("7.0")) {
                version = " 11";
            } else if (version.equals("6.0")) {
                version = " 10";
            } else if (version.equals("5.0")) {
                version = " 9";
            } else if (version.equals("4.0")) {
                version = " 8";
            }

            if (StringUtils.isNotEmpty(version)) {
                browser += version;
            }
        } else if (userAgent.indexOf("Opera") > -1) {
            browser = "Opera";
        } else if (userAgent.indexOf("Firefox") > -1) {
            browser = "Firefox";
        } else if (userAgent.indexOf("Safari") > -1) {
            if (userAgent.indexOf("Chrome") > -1) {
                browser = "Chrome";
            } else {
                browser = "Safari";
            }
        }
        browser += " : " + userAgent;
        return browser;
    }

    public static String getClientIp(HttpServletRequest request) {
        String requestIp = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isEmpty(requestIp)) {
            requestIp = request.getRemoteAddr();
        }
        return requestIp;
    }
}
