package com.fukkatsummi.asiia.exception;

public class LedStripNotFoundException extends RuntimeException {
    public LedStripNotFoundException(Long id) {
        super("Could not find device " + id);
    }
}
