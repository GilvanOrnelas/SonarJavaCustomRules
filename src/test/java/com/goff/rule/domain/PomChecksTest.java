package com.goff.rule.domain;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.PomPropertiesChecks;
import com.goff.rule.infra.reader.PomFile;

public class PomChecksTest {

    @Test
    public final void givenProperyAndValue_shallExistThePropertyWithThatValue() {
        final PomPropertiesChecks check = new PomPropertiesChecks();
        final String property = "java.plugin.version";
        final String value = "3.8";
        check.propertiesAndValues = property + "=" + value;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }


    @Test
    public void thePomVersionTagShallBeAppVersion() {
        final boolean result = propertyHasExpectedValue("version", "1.0-SNAPSHOT");
        Assert.assertTrue(result);
    }

    private boolean propertyHasExpectedValue(final String property, final String expectedValue) {
        final String file =
                PomFile.getStringFormatFromFile("src/test/filesSamplesToEvaluate/pomChecks/pomSample.xml");

        final boolean propertyIsCorrect = PomFile.propertyHasExpectedValue(file, property, expectedValue);
        return propertyIsCorrect;
    }

}
