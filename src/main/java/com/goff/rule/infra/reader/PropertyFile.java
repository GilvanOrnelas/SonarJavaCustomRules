package com.goff.rule.infra.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyFile {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyFile.class);

    public static String getStringFormatFromFile(final String absolutePath) {
        final Path path = Paths.get(absolutePath);
        try {
            return new String(Files.readAllBytes(path));
        }
        catch (final IOException e) {
            errorOnFileReadingLog(absolutePath, e);
            return "";
        }
    }

    public static List<String> getListStringFormatFromFile(final String absolutePath) {
        final Path path = Paths.get(absolutePath);
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8"));
        }
        catch (final IOException e) {
            errorOnFileReadingLog(absolutePath, e);
            return Collections.emptyList();
        }
    }

    public static boolean propertyHasExpectedValue(final String file, final String property,
            final String expectedValue) {
        final Pattern propertyPattern = Pattern.compile("<" + property + ">" + expectedValue);
        final Matcher propertyMatcher = propertyPattern.matcher(file);

        final boolean propertyIsCorrect = propertyMatcher.find();

        return propertyIsCorrect;
    }

    private static void errorOnFileReadingLog(final String absolutePath, final IOException e) {
        final String erroMsg = "Não foi possível ler o arquivo" + absolutePath;
        LOG.error(erroMsg);
        LOG.debug(erroMsg, e);
    }

}
