package com.udacity.jdnd.course3.critter.schedule.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Schedule not found")
public class ScheduleException extends  RuntimeException{

    public ScheduleException (String message){
        super(message);
    }
}
