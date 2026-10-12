package com.gsitm.framework.security.core.authority;

import com.gsitm.framework.core.log.properties.LogActiveProperties;
import com.gsitm.framework.core.properties.ViewProperties;
import com.gsitm.framework.core.utils.WebSupportUtils;
import com.gsitm.framework.security.core.log.UstraAccessLog;
import com.gsitm.framework.security.core.log.UstraAccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <pre>
 * com.gsitm.framework.security.core.authority
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/23
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthorizationChecker {

    private final UstraDynamicAuthorityChecker ustraDynamicAuthorityChecker;
    private final UstraAccessLogService ustraAccessLogService;
    private final ViewProperties viewProperties;
    private final LogActiveProperties logActiveProperties;

    /**
     * 해당 URL에 대한 권한이 있는지 체크
     * @param request
     * @param authentication
     * @return
     */
    public boolean check(HttpServletRequest request, Authentication authentication) {
        boolean access = ustraDynamicAuthorityChecker.check(request, authentication);
        insertLog(request, authentication, access);
        if (access) {
            return true;
        }
        return false;
    }

    private void insertLog(HttpServletRequest request, Authentication authentication, boolean access) {
        if (!logActiveProperties.getAccess()) {
            return;
        }
        try {
            String uri = request.getRequestURI();
            UstraAccessLog ustraAccessLog = new UstraAccessLog();
            ustraAccessLog.setUri(uri);
            ustraAccessLog.setMethod(request.getMethod());
            ustraAccessLog.setUserAgent(WebSupportUtils.getUserAgent(request.getHeader("User-Agent")));
            ustraAccessLog.setAccessDateTime(LocalDateTime.now());
            ustraAccessLog.setUserId(authentication.getName());
            ustraAccessLog.setUserIp(WebSupportUtils.getClientIp(request));
            ustraAccessLog.setReferer(request.getHeader("REFERER"));

            if (StringUtils.isNotEmpty(viewProperties.getSuffix()) && viewProperties.getSuffix().equals(uri.substring(uri.lastIndexOf("/")))) {
                ustraAccessLog.setView(true);
            } else {
                ustraAccessLog.setView(false);
            }

            ustraAccessLogService.insertAccessLog(ustraAccessLog);
        } catch (Exception ex) { // 비즈니스 로직 영향
            log.error(ExceptionUtils.getStackTrace(ex));
        }

    }
}
