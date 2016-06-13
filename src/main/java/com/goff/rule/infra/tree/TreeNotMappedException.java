package com.goff.rule.infra.tree;

class TreeNotMappedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    TreeNotMappedException(final String exception) {
        super(exception);
    }

    TreeNotMappedException(final String exception, final Throwable cause) {
        super(exception, cause);
    }
}
