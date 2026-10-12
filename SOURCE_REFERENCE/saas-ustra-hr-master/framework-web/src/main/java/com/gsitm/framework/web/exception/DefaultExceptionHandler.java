package com.gsitm.framework.web.exception;

import com.gsitm.framework.core.exception.UstraExceptionLogService;
import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.web.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageUtils messageUtils;
    private final Environment environment;
    private final UstraExceptionLogService ustraExceptionLogService;

    private final String LOCAL = "local";
    private final String LOG = "log";
    private final String DEBUG = "debug";

    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        Throwable cause = ExceptionUtils.getRootCause(e);
        String[] profiles = environment.getActiveProfiles();

        if (Arrays.asList(profiles).contains(LOCAL) || Arrays.asList(profiles).contains(DEBUG)) {
            e.printStackTrace();
        }

        if (Arrays.asList(profiles).contains(LOG)) {
            ustraExceptionLogService.insert(request, e);
        }

        if (cause instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            String message = serviceException.getMessage();
            if (serviceException.getMessageCode()!=null) {
                message = getMessage(serviceException.getMessageCode().getValue(), serviceException.getMessageArgs());
            }
            serviceException.setMessage(message);
        } else {
            String message = getMessage("ustra.base.error", new String[] {});
            e = new Exception(message);
        }

        return determineResponseObject(request, e);
    }

    private Object determineResponseObject(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        String context = request.getContextPath();
        if (!"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){

            //Ajax 요청이 아닐때 Exception 처리
        } else {
            modelAndView.setView(new MappingJackson2JsonView());
        }
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }

    protected String getMessage(String code, String[] args) {
        List<String> langList = new ArrayList<>();
        String[] langArgs;
        if (args != null && args.length > 0 ) {
            for (String langCode : args) {
                try {
                    langList.add(messageUtils.getMessage(langCode, null));
                } catch (NullPointerException e) {
                    langList.add(langCode);
                } catch (Exception e) {
                    langList.add(langCode);
                }

            }
        }
        langArgs = new String[langList.size()];
        langArgs = langList.toArray(langArgs);
        return messageUtils.getMessage(code, langArgs);
    }

}
