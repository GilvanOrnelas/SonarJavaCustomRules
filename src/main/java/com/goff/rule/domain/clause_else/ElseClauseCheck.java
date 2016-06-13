package com.goff.rule.domain.clause_else;

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "ElseClauseCheck", name = Constante.PREFIX
        + "Verifica se a cláusula \"else\" é necessária.", description = "Verifica se a cláusula \"else\" é necessária.", tags = {
                "GOFF", "java" }, priority = Priority.MINOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("5min")
public class ElseClauseCheck extends SonarBaseTreeRule {

    @Override
    public void visitIfStatement(final IfStatementTree tree) {
        final List<StatementTree> statementsFromIfBlock = ((BlockTree) tree.thenStatement()).body();

        if (allIfsHaveTheReturnStatement(statementsFromIfBlock) && elseClauseExists(tree))
            addIssue(tree, "Cláusula \"else\" é desnecessária neste bloco.");
    }

    private boolean elseClauseExists(final IfStatementTree tree) {
        return tree.elseStatement() != null;
    }

    private boolean allIfsHaveTheReturnStatement(final List<StatementTree> statementsFromIfBlock) {
        for (final StatementTree statement : statementsFromIfBlock)
            if (hasReturnStatement(statement))
                return true;
        return false;
    }

    private boolean hasReturnStatement(final StatementTree statement) {
        return statement.kind() == Kind.RETURN_STATEMENT;
    }
}
