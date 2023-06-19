package com.ylhl.phlab.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException{
    int code = 500;
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(int code,String message) {
        super(message);
        this.code =code;
    }
}
