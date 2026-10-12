package com.gsitm.framework.core.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.gsitm.framework.core.log.properties
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/10
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix="framework.log.active", ignoreUnknownFields=false)
public class LogActiveProperties {
    private Boolean login = true;
    private Boolean access = true;
    private Boolean filterBefore = false;
    private Boolean filterAfter = false;
}
