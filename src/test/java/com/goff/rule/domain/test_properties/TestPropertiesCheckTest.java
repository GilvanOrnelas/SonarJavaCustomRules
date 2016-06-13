package com.goff.rule.domain.test_properties;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class TestPropertiesCheckTest {

    @Test
    public final void givenTheTestProperties_themShallExistInTheTestPropertiesFile() {
        final TestPropertiesCheck check = new TestPropertiesCheck();

        check.propertiesAndValues = "serverAdress=http://localhost:8080/teste/rest;#jbossHome=/home/servers/jboss-eap-6.2";

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }

}
