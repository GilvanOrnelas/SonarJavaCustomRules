package com.goff.rule.domain.object;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.object.NullReturnCheck;

public class NullReturnCheckTest {

    private NullReturnCheck check;

    @Before
    public void load() {
        check = new NullReturnCheck();
    }

    @Test
    public void givenNullReturnedAsResult_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/object/NullReturn.java", check);
    }

    @Test
    public void givenVoidReturnStatement_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/object/DefaultNullReturn.java", check);
    }

}
