package com.gsitm.framework.core.exception;

import com.gsitm.framework.core.MessageCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServiceException extends RuntimeException {
    private String message;
    private MessageCode messageCode;
    private String[] messageArgs;

    public ServiceException(String messageCode) {
        this.message = messageCode;
        this.messageArgs = null;
    }


    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public ServiceException(String message, String[] messageArgs) {
        this.message = message;
        this.messageArgs = messageArgs;
    }

    public ServiceException(String message, String[] messageArgs, Throwable cause) {
        super(cause);
        this.message = message;
        this.messageArgs = messageArgs;
    }


    public ServiceException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
    public ServiceException(MessageCode messageCode, Throwable cause) {
        super(cause);
        this.messageCode = messageCode;
    }

    public ServiceException(MessageCode messageCode, String[] messageArgs) {
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
    }

    public ServiceException(MessageCode messageCode, String[] messageArgs, Throwable cause) {
        super(cause);
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
    }
}
