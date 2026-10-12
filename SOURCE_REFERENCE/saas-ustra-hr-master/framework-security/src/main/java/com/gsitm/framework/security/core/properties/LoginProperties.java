package com.gsitm.framework.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.gsitm.framework.security.core.config
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/25
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="framework.login", ignoreUnknownFields=false)
public class LoginProperties {

    private String defaultUri;
    private String processUri;
    private String successUri;
    private String failUri;
    private String idParam;
    private String loginKey;
    private String localUri;

//    private String defaultUri = "/login";
//    private String processUri = "/login";
//    private String successUri = "/";
//    private String failUri = "/login";
//    private String idParam = "username";
//    private String loginKey = "__user__";
//
//    private String localUri = "/login/local";
}
