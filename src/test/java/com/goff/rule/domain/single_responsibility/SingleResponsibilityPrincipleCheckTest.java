package com.goff.rule.domain.single_responsibility;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.single_responsibility.SingleResponsibilityPrincipleCheck;

public class SingleResponsibilityPrincipleCheckTest {

    private SingleResponsibilityPrincipleCheck check;

    @Before
    public void load() {
        check = new SingleResponsibilityPrincipleCheck();
        check.layersToEvaluate = "application";
    }

    @Test
    public void givenFieldNotUsedByAllMethods_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/field/FieldNotUsebByAllMethods.java", check);
    }

    @Test
    public void givenAbstractMethod_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/AbstractMethod.java", check);
    }

    @Test
    public void givenNewClassCall_shallHasOneIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/NewClass.java", check);
    }

    @Test
    public void givenMethodCallInReturnStatement_shallHasOneIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/MethodCallInReturn.java", check);
    }

    @Test
    public void givenFieldUsedByAllMethods_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/FieldUsebByAllMethods.java", check);
    }

    @Test
    public void givenFieldUsedByAllMethodsAsArgument_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/FieldUsebByAllMethodsAsArgument.java", check);
    }

    @Test
    public void givenFieldUsedByPublicMethodInPrivateWithWhileBlock_shallHasNoIssue() {
        JavaCheckVerifier
                .verifyNoIssue(
                        "src/test/filesSamplesToEvaluate/field/FieldUsebByPublicMethodInPrivateWithWhileBlock.java",
                        check);
    }

    @Test
    public void givenFieldUsebByPublicMethodInsidePrivateMethodsRecursively_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/FieldUsebByMethodsIndirectlyRecursive.java", check);
    }

    @Test
    public void givenCallToMethodWithAnnotation_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/CallingMethodWithAnnotation.java",
                check);
    }

    @Test
    public void givenMethodUsingFieldExpressionAsReturn_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ReturningMethodExpression.java",
                check);
    }

    @Test
    public void givenMethodReturningTheFieldDirectly_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ReturningField.java", check);
    }

    @Test
    public void givenMethodReturningTheFieldInTheArgument_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/ReturningMethodFieldInArgument.java", check);
    }

    @Test
    public void givenFieldBeingCalledToAssignSomeVariable_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/UsebByAllMethodAsObjectCallToAssign.java", check);
    }

    @Test
    public void givenFieldBeingCalledToSetSomeValue_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/UsebByAllMethodAsObjectCallToSet.java", check);
    }

    @Test
    public void givenFieldBeingCalledInsideBlockIfElse_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ObjectCallInsideBlockIfElse.java",
                check);
    }

    @Test
    public void givenFieldBeingCalledInsideIfElseExpression_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ExpressionOfIfClause.java", check);
    }

    @Test
    public void givenFieldBeingCalledInsideBlockFor_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ObjectCallInsideBlockFor.java",
                check);
    }

    @Test
    public void givenFieldBeingCalledInsideBlockWhile_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ObjectCallInsideBlockWhile.java",
                check);
    }

    @Test
    public void givenFieldUsedByPublicMethodsInPrivatesInForBlock_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/FieldUsebByPublicMethodInPrivateWithForBlock.java", check);
    }

    @Test
    public void givenFieldUsedByPublicMethodsAllCases_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue(
                "src/test/filesSamplesToEvaluate/field/FieldUsebByPublicMethodInPrivatesAllCases.java", check);
    }

    @Test
    public void givenFieldCallAsParameters_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/FieldInArgument.java", check);
    }

    @Test
    public void givenConstantNotUsedByAllMethods_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/ConstantNotUsedByAllMethods.java",
                check);
    }

    @Test
    public void givenFieldsInTryCatchBlock_shallHasNoIssue() {
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/TryCatchBlock.java", check);
    }

    @Test
    public void givenManyCallsInTheSameSession_shallCleanTheObjectsBetweenTheCalls() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/field/FieldNotUsebByAllMethods.java", check);
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/field/FieldUsebByAllMethods.java", check);
    }
}
