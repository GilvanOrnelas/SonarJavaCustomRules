package com.goff.rule.domain.method;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.method.MethodOutsideLayersCheck;

public class MethodOutsideLayersCheckTest {

    private final MethodOutsideLayersCheck check = new MethodOutsideLayersCheck();;

    @Before
    public void load() {
        check.allowedLayers = "infrastructure;domain;application;rest";
    }

    @Test
    public void givenMethodOutsideAllowedLayers_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/methods/ClassOutsideTheLayers.java", check);
    }

    @Test
    public void givenMethodInsideInnerClassInAllowedLayers_shallNotHasAnyIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/methods/InnerClassInsideTheLayers.java",
                check);
    }

    @Test
    public void givenMethodInsideAllowedLayers_shallNotHasAnyIssue() {
        JavaCheckVerifier
                .verifyNoIssue("src/test/filesSamplesToEvaluate/methods/ClassInsideTheLayers.java", check);
    }

    @Test
    public void givenMethodOutsideAllowedLayersWithOnlyConstants_shallNotHasAnyIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/methods/ClassOutsideTheLayersWithConstructor.java", check);
    }
}
