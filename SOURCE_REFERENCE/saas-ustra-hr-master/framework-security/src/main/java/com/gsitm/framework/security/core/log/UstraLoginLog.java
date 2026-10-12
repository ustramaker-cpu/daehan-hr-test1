package com.gsitm.framework.security.core.log;

import com.gsitm.framework.core.utils.WebSupportUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Setter
@Getter
@ToString
public class UstraLoginLog {

    public UstraLoginLog(HttpServletRequest request) {
        this.setUserAgent(WebSupportUtils.getUserAgent(request.getHeader("User-Agent")));
        this.setUserIp(WebSupportUtils.getClientIp(request));
        this.setReferer(request.getHeader("REFERER"));
    }

    private String userId;
    private String userIp;
    private String referer;
    private String userAgent;
    private LocalDateTime loginDateTime;
    private boolean isSuccess;
}
