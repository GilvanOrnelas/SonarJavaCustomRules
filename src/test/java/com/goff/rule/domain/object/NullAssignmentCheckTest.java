package com.goff.rule.domain.object;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.object.NullAssignmentCheck;

public class NullAssignmentCheckTest {

    private NullAssignmentCheck check;

    @Before
    public void load() {
        check = new NullAssignmentCheck();
    }

    @Test
    public void givenNullAssignedToObject_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/object/NullAssignment.java", check);
    }

    @Test
    public void givenStaticNullAssignedToObject_shallHasOneIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/object/DefaultValueAssignment.java",
                check);
    }

}
