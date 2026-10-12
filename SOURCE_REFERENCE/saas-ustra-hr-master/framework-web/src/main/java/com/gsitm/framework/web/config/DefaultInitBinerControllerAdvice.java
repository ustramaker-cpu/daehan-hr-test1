package com.gsitm.framework.web.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class DefaultInitBinerControllerAdvice {

    private static int MAXIMUM_LIMIT = 100000;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(MAXIMUM_LIMIT);
    }
}
