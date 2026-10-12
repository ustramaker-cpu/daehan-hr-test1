package com.gsitm.framework.security.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="framework.logout", ignoreUnknownFields=false)
public class LogoutProperties {
    private String successUri = "";
}
