package com.goff.rule.domain.annotation;

import org.junit.Before;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.annotation.AnnotationPlaceCheck;

public class AnnotationPlaceCheckTest {

    AnnotationPlaceCheck check;

    @Before
    public void load() {
        check = new AnnotationPlaceCheck();
    }

    @Test
    public void givenAnnotationInField_shallHasOneIssue() {
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/annotation/FieldAnnotation.java", check);
    }

    @Test
    public void givenValidAnnotationInField_shallHasNoIssue() {
        check.validFieldAnnotations = "Field";
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/annotation/ValidFieldAnnotation.java",
                check);
    }
}
