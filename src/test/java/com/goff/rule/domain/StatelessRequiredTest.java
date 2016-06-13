package com.goff.rule.domain;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.StatelessRequiredCheck;

public class StatelessRequiredTest {

    private StatelessRequiredCheck check;

    @Before
    public void createTransactionalAttributeMock() {
        check = new StatelessRequiredCheck();

    }

    public void givenClassWithoutThestatelessAnnotation_shallHasAnIssue() {
        JavaCheckVerifier
                .verify("src/test/filesSamplesToEvaluate/statelessRequired/StatelessFailWithoutAnyAnnotations.java",
                        check);
    }

    public void givenClassWithOnlyOthersAnnotations_shallHasAnIssue() {
        JavaCheckVerifier.verify(
                "src/test/filesSamplesToEvaluate/statelessRequired/StatelessFailWithOnlyOthersAnnotations.java",
                check);

    }

    @Test
    public void givenClassWithstatelessAnnotation_shallNotHasAnIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/statelessRequired/StatelessRequiredSuccess.java", check);
    }

    public void givenClassNotInTheApplicationPackage_shallNotHasAnIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/statelessRequired/StatelessSuccessOtherPackage.java", check);
    }

}
