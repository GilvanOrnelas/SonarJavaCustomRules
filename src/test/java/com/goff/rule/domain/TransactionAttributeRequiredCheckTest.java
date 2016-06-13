package com.goff.rule.domain;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.TransactionAttributeRequiredCheck;

public class TransactionAttributeRequiredCheckTest {

    private TransactionAttributeRequiredCheck check;

    @Before
    public void createTransactionalAttributeMock() {
        check = new TransactionAttributeRequiredCheck();

    }

    public void givenClassWithoutTheTransactionalAttributeAnnotation_shallHasAnIssue() {
        JavaCheckVerifier
                .verify("src/test/filesSamplesToEvaluate/transactionAttributeRequired/TransactionAttributeRequiredFailWithoutAnnotations.java",
                        check);
    }

    public void givenClassWithOnlyOthersAnnotations_shallHasAnIssue() {
        JavaCheckVerifier
                .verify("src/test/filesSamplesToEvaluate/transactionAttributeRequired/TransactionAttributeRequiredFailOtherAnnotations.java",
                        check);

    }

    @Test
    public void givenClassWithTransactionalAttributeAnnotation_shallNotHasAnIssue() {
        JavaCheckVerifier
                .verifyNoIssue(
                        "src/test/filesSamplesToEvaluate/transactionAttributeRequired/TransactionAttributeRequiredSuccess.java",
                        check);
    }

    @Test
    public void givenClassWithNotSupportedAnnotation_shallNotHasAnIssue() {
        JavaCheckVerifier
                .verifyNoIssue(
                        "src/test/filesSamplesToEvaluate/transactionAttributeRequired/TransactionAttributeRequiredNotSupportedSuccess.java",
                        check);
    }

    public void givenClassNotInTheApplicationPackage_shallNotHasAnIssue() {
        JavaCheckVerifier
                .verifyNoIssue(
                        "src/test/filesSamplesToEvaluate/transactionAttributeRequired/TransactionAttributeRequiredSuccessOtherPackage.java",
                        check);
    }

}
