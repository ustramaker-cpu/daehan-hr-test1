package com.gsitm.framework.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.gsitm.framework.core.subfix
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/25
 */

@Getter @Setter
@Component
@ConfigurationProperties(prefix="framework.view", ignoreUnknownFields=false)
public class ViewProperties {
    private String  suffix = "/view";
}
