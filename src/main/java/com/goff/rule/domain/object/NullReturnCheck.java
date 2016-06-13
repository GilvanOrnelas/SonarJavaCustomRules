package com.goff.rule.domain.object;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "NullObjectCheck", name = Constante.PREFIX
        + "Null não deve ser retornado.", description = "Null não deve ser retornado.", tags = { "GOFF",
                "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("5min")
public class NullReturnCheck extends SonarBaseTreeRule {

    @Override
    public void visitReturnStatement(final ReturnStatementTree tree) {
        if (returnIsNull(tree))
            addIssue(tree, "Null não deve ser retornado.");
    }

    private boolean returnIsNull(final ReturnStatementTree tree) {
        final ExpressionTree returnValue = tree.expression();
        return (returnValue != null) && (Kind.NULL_LITERAL == returnValue.kind());
    }
}
