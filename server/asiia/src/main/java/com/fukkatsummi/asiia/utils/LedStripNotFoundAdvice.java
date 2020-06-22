package com.fukkatsummi.asiia.utils;

import com.fukkatsummi.asiia.exception.DeviceNotFoundException;
import com.fukkatsummi.asiia.exception.LedStripNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LedStripNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(LedStripNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String deviceNotFoundHandler(LedStripNotFoundException ex) {
        return ex.getMessage();
    }
}
