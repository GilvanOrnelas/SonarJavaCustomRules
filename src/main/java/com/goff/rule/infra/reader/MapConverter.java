package com.goff.rule.infra.reader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MapConverter {

    private MapConverter() {
        super();
    }

    public static Map<String, String> convertStringToMap(final String propertiesAndValues) {
        final Map<String, String> properties = new ConcurrentHashMap<>();
        for (final String property : propertiesAndValues.split(";"))
            properties.putAll(defineKeyAndValue(property));

        return properties;
    }

    private static Map<String, String> defineKeyAndValue(final String property) {
        final Map<String, String> properties = new ConcurrentHashMap<>();
        final String[] keyAndValue = property.split("=");
        final String key = keyAndValue[0];
        final String value = keyAndValue[1];
        properties.put(key, value);
        return properties;
    }
}
