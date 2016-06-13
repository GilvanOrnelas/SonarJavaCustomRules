package com.goff.rule.infra.tree;

public class ClassUnreadableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClassUnreadableException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
