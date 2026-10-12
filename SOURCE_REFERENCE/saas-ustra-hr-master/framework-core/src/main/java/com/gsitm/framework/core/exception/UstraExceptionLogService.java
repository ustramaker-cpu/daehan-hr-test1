package com.gsitm.framework.core.exception;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * com.gsitm.framework.web.config
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/24
 */
public interface UstraExceptionLogService {

    void insert (HttpServletRequest request, Exception exception);
}
