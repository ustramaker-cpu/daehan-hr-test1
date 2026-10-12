package com.gsitm.framework.security.core.log;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
@Getter
@Setter
public class UstraAccessLog {
    private String uri;
    private String method;
    private String userId;
    private String userIp;
    private String referer;
    private String userAgent;
    private LocalDateTime accessDateTime;
    private boolean isView;
    private HttpServletRequest httpServletRequest;
    private Authentication authentication;
}
