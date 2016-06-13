package com.goff.rule.infra.exception;

public class ReflectionCreateInstanceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReflectionCreateInstanceException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
