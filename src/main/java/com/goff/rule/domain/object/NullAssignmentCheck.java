package com.goff.rule.domain.object;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "NullAssignmentCheck", name = Constante.PREFIX
        + "Null não devem ser atribuído a variáveis.", description = "Null não devem ser atribuído a variáveis.", tags = {
                "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("5min")
public class NullAssignmentCheck extends SonarBaseTreeRule {

    @Override
    public void visitVariable(final VariableTree tree) {
        if (isAssignToNull(tree))
            addIssue(tree, "Null não devem ser atribuído a variáveis.");
    }

    private boolean isAssignToNull(final VariableTree tree) {
        final ExpressionTree initializerValue = tree.initializer();
        return (initializerValue != null) && (Kind.NULL_LITERAL == initializerValue.kind());
    }
}
