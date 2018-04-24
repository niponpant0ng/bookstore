package com.service.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by nipon on 4/24/18.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CredentialWrongException extends RuntimeException {
    public CredentialWrongException() {
        super("Your credential was wrong");
    }
}
