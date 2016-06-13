package com.goff.rule.domain.dependency;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.dependency.DependencyScopeCheck;

public class DependencyScopeCheckTest {

    @Test
    public final void givenDependencyArtifactIdAndScope_thePomDependencyScopeShallBeTheSameAsInformed() {
        final DependencyScopeCheck check = new DependencyScopeCheck();
        final String artifactId = "log4j";
        final String scope = "provided";
        check.dependenciesAndScopes = artifactId + "=" + scope;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }

}
