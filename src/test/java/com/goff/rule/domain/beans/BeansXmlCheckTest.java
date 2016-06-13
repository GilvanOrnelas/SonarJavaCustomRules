package com.goff.rule.domain.beans;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BeansXmlCheckTest extends BeansXmlTest {

    private static final String ALTERNATIVE = "com.goff.security.Authentication";
    private static final String BEANS_XML_PATH = "src/main/webapp/WEB-INF/beans.xml";

    public BeansXmlCheckTest() {
        super(BEANS_XML_PATH, ALTERNATIVE);
    }

    @Test
    public void givenBeansXmlFile_shallBeAbleToReadTheAlternatives_() {
        super.givenBeansXmlFile_shallBeAbleToReadTheAlternatives();
    }

    @Test
    public void givenAlternativeThatExists_shallNotHaveAnyMissingAlternative_() {
        super.givenAlternativeThatExists_shallNotHaveAnyMissingAlternative();

    }

    @Test
    public void givenThatTheBeansXmlHasTheCorrectAlternative_theBeansXmlshallBeValid_() {
        final BeansXmlCheck check = new BeansXmlCheck();
        check.requiredAlternatives = ALTERNATIVE;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }
}
