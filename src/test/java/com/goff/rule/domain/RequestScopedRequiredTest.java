package com.goff.rule.domain;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.RequestScopedRequiredCheck;

public class RequestScopedRequiredTest {

    private RequestScopedRequiredCheck check;

    @Before
    public void createTransactionalAttributeMock() {
        check = new RequestScopedRequiredCheck();

    }

    @Test
    public void givenClassWithoutTheRequestScopedAnnotation_shallHasAnIssue() {
        JavaCheckVerifier
                .verify("src/test/filesSamplesToEvaluate/requestScopedRequired/RequestScopedFailWithoutAnyAnnotations.java",
                        check);
    }

    @Test
    public void givenClassWithOnlyOthersAnnotations_shallHasAnIssue() {
        JavaCheckVerifier
                .verify("src/test/filesSamplesToEvaluate/requestScopedRequired/RequestScopedFailWithOnlyOthersAnnotations.java",
                        check);

    }

    @Test
    public void givenClassWithRequestScopedAnnotation_shallNotHasAnIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/requestScopedRequired/RequestScopedRequiredSuccess.java", check);
    }

    @Test
    public void givenClassNotInTheRestPackage_shallNotHasAnIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/requestScopedRequired/RequestScopedSuccessOtherPackage.java",
                check);
    }
}
