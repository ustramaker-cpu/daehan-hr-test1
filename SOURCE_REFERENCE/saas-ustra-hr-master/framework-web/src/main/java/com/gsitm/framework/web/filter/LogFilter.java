package com.gsitm.framework.web.filter;

import com.gsitm.framework.core.log.properties.LogActiveProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * com.gsitm.framework.web.filter
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/10
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class LogFilter implements Filter {

    private final UstraFilterLogService ustraFilterLogService;
    private final LogActiveProperties logActiveProperties;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (logActiveProperties.getFilterBefore()) {
            ustraFilterLogService.insertBeforeLog((HttpServletRequest) req);
        }

        chain.doFilter(req, res);

        if (logActiveProperties.getFilterAfter()) {
            ustraFilterLogService.insertAfterLog((HttpServletRequest) req, (HttpServletResponse) res);
        }

    }

    public void init(FilterConfig filterConfig) {
        // not needed
    }

    public void destroy() {
        //not needed
    }
}