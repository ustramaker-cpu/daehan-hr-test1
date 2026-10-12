package com.gsitm.framework.security.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.gsitm.framework.security.jwt.properties
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
@ConfigurationProperties(prefix="framework.security.jwt", ignoreUnknownFields=false)
public class JwtProperties {
    private String keycloakName = "KEYCLOAK_TOKEN";
    private Boolean keycloakEnabled = false;

    private String secretKey;
    private int validMinute;
    private String tokenKey;
    private int rvalidMinute;
    private String rtokenKey;
    private String serverName;
    private Boolean activeDbManage = false;
}
