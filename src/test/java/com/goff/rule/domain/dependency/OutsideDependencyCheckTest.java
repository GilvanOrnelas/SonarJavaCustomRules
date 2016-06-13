package com.goff.rule.domain.dependency;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.dependency.OutsideDependencyCheck;

public class OutsideDependencyCheckTest {

    @Test
    public final void givenDependencyArtifactId_shallPermitTheDependencyToExist() {
        final OutsideDependencyCheck check = new OutsideDependencyCheck();
        check.allowedOutsidesDependencies =
                "sonar-plugin-api;java-checks-testkit;sonar-java-plugin;fest-assert;"
                        + "java-squid;sslr-squid-bridge;sslr-testing-harness;junit;log4j;";

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }

}
