package com.udacity.jdnd.course3.critter.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer not found")
public class CustomerException extends RuntimeException{

    public CustomerException (String message){super(message);}
}
