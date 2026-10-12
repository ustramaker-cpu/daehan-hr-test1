package com.gsitm.framework.security.core.log;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * com.gsitm.framework.security.core.log
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/24
 */
public interface UstraAccessLogService {

    void insertAccessLog(UstraAccessLog ustraAccessLog);
}
