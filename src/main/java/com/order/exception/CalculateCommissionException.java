package com.order.exception;

/**
 * Created by tomas on 7/8/19.
 */
public class CalculateCommissionException extends RuntimeException {

    public CalculateCommissionException(String errorMessage) {
        super(errorMessage);
    }
}
