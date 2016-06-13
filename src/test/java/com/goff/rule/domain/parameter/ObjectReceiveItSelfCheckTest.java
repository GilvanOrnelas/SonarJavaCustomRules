package com.goff.rule.domain.parameter;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.parameter.ObjectReceiveItSelfCheck;

public class ObjectReceiveItSelfCheckTest {

    private ObjectReceiveItSelfCheck check;

    @Before
    public void load() {
        check = new ObjectReceiveItSelfCheck();
    }

    @Test
    public void givenObjectHavingItSelfAsParameter_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/parameter/ObjectSample.java", check);
    }

    @Test
    public void givenCheckingEnum_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/parameter/EnumReceivingItSelf.java",
                check);
    }
}
