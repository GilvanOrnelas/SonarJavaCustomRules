package com.goff.rule.domain.packages;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.packages.BelowPackageAccessCheck;

public class BelowPackageAccessCheckTest {

    private final BelowPackageAccessCheck check = new BelowPackageAccessCheck();

    @Test
    public final void givenSubPackageBeingImported_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/packages/AccessingPackageBelow.java", check);
    }

    @Test
    public final void givenClassWithoutSubPackage_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/AccessingValidPackages.java",
                check);
    }

}
