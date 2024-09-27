package br.com.lowlevel.promotion_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundExceptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MyFileNotFoundExceptionException(String ex) {
        super(ex);
    }

    public MyFileNotFoundExceptionException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
