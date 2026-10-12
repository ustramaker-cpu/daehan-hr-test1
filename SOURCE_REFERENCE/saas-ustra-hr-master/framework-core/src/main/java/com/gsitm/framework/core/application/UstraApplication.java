package com.gsitm.framework.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <pre>
 * com.gsitm.framework.core.application
 *
 * </pre>
 * Description :
 *
 * @author : Mercury
 * @version : 1.0
 * @date : 2017-10-23
 */
@ComponentScan(basePackages = "com.gsitm.framework")
public class UstraApplication {

    public static void run(Class clazz, String args[]) {
        SpringApplication.run(clazz, args);
    }
}
