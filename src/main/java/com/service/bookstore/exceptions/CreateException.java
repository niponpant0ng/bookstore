package com.service.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by nipon on 4/23/18.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CreateException extends RuntimeException {
    public CreateException(String modelName) {
        super(String.format("Create %s error", modelName));
    }
}
