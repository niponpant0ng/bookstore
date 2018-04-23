package com.service.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by nipon on 4/23/18.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ManageErrorException extends RuntimeException {
    public ManageErrorException(String modelName) {
        super(String.format("Create or Update %s error", modelName));
    }
}
