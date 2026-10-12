package com.gsitm.framework.web.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>
 * com.gsitm.framework.security.core.log
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/10
 */
public interface UstraFilterLogService {

    void insertBeforeLog(HttpServletRequest request);

    void insertAfterLog(HttpServletRequest request, HttpServletResponse response);
}
