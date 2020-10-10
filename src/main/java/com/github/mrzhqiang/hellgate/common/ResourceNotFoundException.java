package com.github.mrzhqiang.hellgate.common;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3530787478941887904L;

    private ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String message) {
        return new ResourceNotFoundException(message);
    }
}
