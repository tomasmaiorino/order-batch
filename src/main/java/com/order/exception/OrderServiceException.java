package com.order.exception;

public class OrderServiceException extends RuntimeException {

    public OrderServiceException (String errorMessage) {
        super(errorMessage);
    }
}