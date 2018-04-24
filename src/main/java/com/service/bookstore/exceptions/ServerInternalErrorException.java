package com.service.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by nipon on 4/24/18.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerInternalErrorException extends RuntimeException {
    public ServerInternalErrorException() {
        super("Server was some problem");
    }
}
