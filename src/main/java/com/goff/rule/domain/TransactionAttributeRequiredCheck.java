package com.goff.rule.domain;

import java.util.List;
import java.util.ListIterator;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.infra.annotation.AnnotationVerifier;
import com.goff.rule.infra.packages.PackageVerifier;
import com.goff.rule.infra.reflect.PrivateField;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "TransactionAttributeRequired", name = Constante.PREFIX
        + "Anotação TransactionAttribute é obrigatória.", description = "Classes da camada <b>application</b> service devem possuir a anotação TransactionAttribute.", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("10min")
public class TransactionAttributeRequiredCheck extends SonarBaseTreeRule {

    private static final String TRANSACTION_ATTRIBUTE_TYPE = "TransactionAttributeType";
    private static final String NOT_SUPPORTED = "NOT_SUPPORTED";
    private static final String ANNOTATION_NAME = "TransactionAttribute";
    private static final String[] ANNOTATION_REQUIRED_VALUES = { NOT_SUPPORTED,
            TRANSACTION_ATTRIBUTE_TYPE + NOT_SUPPORTED };

    @Override
    public void visitClass(final ClassTree tree) {
        boolean annotationAndValueArePresents = true;
        if (PackageVerifier.isInPackage("application", tree))
            annotationAndValueArePresents = AnnotationVerifier.annotationExists(ANNOTATION_NAME, tree)
                    && requiredValueExists(tree);

        if (!annotationAndValueArePresents)
            context.addIssue(tree, this,
                    "A anotação TransactionAttribute(NOT_SUPPORTED) é obrigatória para classes da camada application.");
    }

    private boolean requiredValueExists(final ClassTree tree) {
        final List<AnnotationTree> annotations = tree.modifiers().annotations();

        for (final AnnotationTree annotationTree : annotations)
            return theValueExists(annotationTree.arguments().listIterator());

        return false;
    }

    private boolean theValueExists(final ListIterator<ExpressionTree> annotationValues) {
        while (annotationValues.hasNext())
            if (valueIsNotSuported(annotationValues))
                return true;

        return false;
    }

    private boolean valueIsNotSuported(final ListIterator<ExpressionTree> annotationValues) {
        final ExpressionTree annotationValue = annotationValues.next();
        for (final String value : ANNOTATION_REQUIRED_VALUES)
            if (annotationValueIsNotSupported(annotationValue, value))
                return true;

        return false;
    }

    private boolean annotationValueIsNotSupported(final ExpressionTree annotationValue, final String value) {
        boolean annotationValueIsNotSupported = false;
        if (value.equals(annotationValue.toString()))
            annotationValueIsNotSupported = true;
        else if (typeIsExplicit(annotationValue))
            annotationValueIsNotSupported = verifyTypeAndValue(annotationValue);
        return annotationValueIsNotSupported;
    }

    private boolean typeIsExplicit(final ExpressionTree annotationValue) {
        return annotationValue.is(Kind.MEMBER_SELECT);
    }

    private boolean verifyTypeAndValue(final ExpressionTree annotationValue) {
        final String type = PrivateField.extractValue(annotationValue, "expression");

        final String value = PrivateField.extractValue(annotationValue, "identifier");

        if (TRANSACTION_ATTRIBUTE_TYPE.equals(type) && NOT_SUPPORTED.equals(value))
            return true;

        return false;
    }

}
