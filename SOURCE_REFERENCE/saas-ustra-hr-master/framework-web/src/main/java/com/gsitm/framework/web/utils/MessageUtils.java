package com.gsitm.framework.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.gsitm.framework.web.utils
 *
 * </pre>
 * Description :
 *
 * @author : Mercury
 * @version : 1.0
 * @date : 2017-07-13
 */
@Component
@RequiredArgsConstructor
public class MessageUtils {

    private final MessageSource messageSource;

    public String getMessage(String code) {
        return this.getMessageLang(code, LocaleContextHolder.getLocale());

    }

    public String getMessageLang(String code, Locale locale) {
        if (code != null) {
            return messageSource.getMessage(code, null, locale);
        }
        return null;
    }

    public String getMessage(String code, String[] args) {
        return this.getMessageLang(code, args, LocaleContextHolder.getLocale());
    }

    public String getMessageLang(String code, String[] args, Locale locale) {
    	if (code != null) {
    		String message = "";
    		try {
    			message = messageSource.getMessage(code, args, locale);
    		}catch (NoSuchMessageException e) {
    			message = code;
    		}
    		return message;
    	}

    	return null;
    }

    public String getMessageCode(String code, String[] args) {
        return this.getMessageCode(code, args, LocaleContextHolder.getLocale());
    }

    public String getMessageCode(String code, String[] args, Locale locale) {
        List<String> langList = new ArrayList<>();
        String[] langArgs;
        for (String arg : args) {
            langList.add(this.getMessage(arg));
        }

        langArgs = new String[langList.size()];
        langArgs = langList.toArray(langArgs);
        if (code != null) {
            return messageSource.getMessage(code, langArgs, locale);
        }
        return null;
    }


}
