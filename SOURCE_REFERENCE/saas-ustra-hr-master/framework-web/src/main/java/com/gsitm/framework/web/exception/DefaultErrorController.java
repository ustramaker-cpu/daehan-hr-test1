package com.gsitm.framework.web.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * com.gsitm.framework.web.exception
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/24
 */
@Controller
public class DefaultErrorController  implements ErrorController {

    private static final String PATH = "/error";

    //@Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return PATH;
    }

    @RequestMapping(value = PATH)
    public String Error(HttpServletRequest req) {
        String status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) + "";
        switch(status) {
            case "403" : return "error/403";
            case "404" : return "error/404";
            case "419" : return "error/419";
            default : return "error/404";
        }

    }
}
