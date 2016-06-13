package com.goff.rule.domain.beans;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BeansXmlTestCheckTest extends BeansXmlTest {

    private static final String ALTERNATIVE = "com.goff.security.TestAutentication";
    private static final String BEANS_XML_TEST_PATH = "src/test/resources/META-INF/beans.xml";

    public BeansXmlTestCheckTest() {
        super(BEANS_XML_TEST_PATH, ALTERNATIVE);
    }

    @Test
    public void givenAlternativeThatExists_shallNotHaveAnyMissingAlternative_() {
        super.givenAlternativeThatExists_shallNotHaveAnyMissingAlternative();

    }

    @Test
    public void givenThatTheBeansXmlHasTheCorrectAlternative_theBeansXmlshallBeValid_() {
        final BeansXmlTestCheck check = new BeansXmlTestCheck();
        check.requiredAlternatives = ALTERNATIVE;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }

    @Test
    public final void givenMoreThanOneAlternative_shallReturnTheExatAmountOfAlternatives() {

        final Set<String> alternatives = new BeansXml(BEANS_XML_TEST_PATH).findAllternatives();

        Assert.assertEquals(2, alternatives.size());
    }
}
