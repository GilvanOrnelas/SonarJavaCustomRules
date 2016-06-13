package com.goff.rule.infra.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class PomFile extends PropertyFile {

    private static final String POM_XML = "pom.xml";

    private static final String POM_FILE = getStringFormatFromFile(POM_XML);
    private static final List<String> POM_LINES = getListStringFormatFromFile(POM_XML);

    private PomFile() {
        super();
    }

    public static List<String> findMissingPropertiesFrom(final Map<String, String> propertyAndValue) {
        final List<String> missingProperties = new ArrayList<>();
        for (final Entry<String, String> propertyEntry : propertyAndValue.entrySet())
            missingProperties.add(missingProperty(propertyEntry));

        missingProperties.remove("");
        return missingProperties;
    }

    private static String missingProperty(final Entry<String, String> property) {
        final String key = property.getKey();
        final String value = property.getValue();
        if (!propertyHasExpectedValue(POM_FILE, key, value))
            return String.format("<%s>%s</%s>", key, value, key);
        return "";
    }

    public static String findDependencies() {
        final StringBuilder dependencies = new StringBuilder();
        final PomDependencyTagLine pomLine = new PomDependencyTagLine();
        for (final String line : POM_LINES)
            if (pomLine.isFromDependencyTag(line))
                dependencies.append(line);

        return dependencies.append(PomDependencyTagLine.DEPENDENCIES_END_TAG).toString();
    }

}
