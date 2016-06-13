package com.goff.rule.domain.closable_resource;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.closable_resource.ClosableResourceCheck;

public class ClosableResourceCheckTest {

    private final ClosableResourceCheck check = new ClosableResourceCheck();

    @Test
    public void givenResourceBeingClosedInFinallyBlock_shallHasOneIssue() {
        JavaCheckVerifier.verify(
                "src/test/filesSamplesToEvaluate/closable_resource/ClosingResourceInFinally.java", check);
    }

    @Test
    public void givenFinallyWithoutCloseableResource_shallNotHasAnyIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/closable_resource/FinallyWithoutCloseable.java", check);
    }
}
