package com.goff.rule.infra.reflect;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PrivateField {

    private static final Logger LOG = LoggerFactory.getLogger(PrivateField.class);

    private PrivateField() {
        super();
    }

    public static String extractValue(final Object object, final String name) {
        try {
            final Field expression = object.getClass().getDeclaredField(name);
            expression.setAccessible(true);
            return expression.get(object).toString();
        }
        catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            final String privateFieldReadError = "Erro ao ler campo privado.";
            LOG.error(privateFieldReadError);
            LOG.debug(privateFieldReadError, e);
            return "";
        }
    }
}
