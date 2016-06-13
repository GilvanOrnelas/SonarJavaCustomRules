package com.goff.rule.domain.annotation;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "AnnotationPlaceCheck", name = Constante.PREFIX
        + "Anotações devem ser colocadas no método/construtor.", description = "Anotações devem ser colocadas no método/construtor.", tags = {
                "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("5min")
public class AnnotationPlaceCheck extends SonarBaseTreeRule {

    @RuleProperty(description = "Anotações aceitas para os campos de classe. Os valores devem ser separados por \";\"", defaultValue = "")
    protected String validFieldAnnotations = "";

    @Override
    public void visitAnnotation(final AnnotationTree tree) {
        final String annotationName = tree.annotationType().toString();
        if (isFieldAnnotation(tree) && isInvalidAnnotation(annotationName))
            addIssue(tree, "A anotação deve ser colocados no método/construtor.");
    }

    private boolean isInvalidAnnotation(final String annotationName) {
        return !validFieldAnnotations.contains(annotationName);
    }

    private boolean isFieldAnnotation(final AnnotationTree tree) {
        final Tree variable = tree.parent().parent();
        final boolean isVariable = variable instanceof VariableTree;
        final boolean isClassVariable = variable.parent() instanceof ClassTree;

        if (isVariable && isClassVariable)
            return true;

        return false;
    }

}
