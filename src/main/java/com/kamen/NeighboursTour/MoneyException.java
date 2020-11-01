package com.kamen.NeighboursTour;

public class MoneyException extends Exception {

    public MoneyException() {
    }

    public MoneyException(String message) {
        super(message);
    }

    public MoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoneyException(Throwable cause) {
        super(cause);
    }

    public MoneyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
