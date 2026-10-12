package com.gsitm.framework.web.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.gsitm.framework.web.properties
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/14
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix="framework.exception", ignoreUnknownFields=false)
public class ExceptionProperties {
    private String errorUri = "/error";
}
