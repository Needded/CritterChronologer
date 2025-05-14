package com.udacity.jdnd.course3.critter.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Employee not found!")
public class EmployeeException extends RuntimeException{

    public EmployeeException (String message){
        super(message);
    }
}
