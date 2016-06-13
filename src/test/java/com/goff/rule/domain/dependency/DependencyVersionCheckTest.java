package com.goff.rule.domain.dependency;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.dependency.DependencyVersionCheck;

public class DependencyVersionCheckTest {

    @Test
    public final void givenDependencyArtifactIdAndVersion_thePomDependencyScopeShallBeTheSameAsInformed() {
        final DependencyVersionCheck check = new DependencyVersionCheck();
        final String artifactId = "log4j";
        final String version = "1.2.17";
        check.dependenciesAndVersions = artifactId + "=" + version;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/pomChecks/SampleSuccessFile.java", check);
    }

}
