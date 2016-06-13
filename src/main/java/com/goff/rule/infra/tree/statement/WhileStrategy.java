package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;

import com.goff.rule.infra.tree.TreeStatement;

public class WhileStrategy extends TreeStatement<WhileStatementTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final WhileStatementTree statement) {
        final Set<String> members = new HashSet<>();
        final WhileStatementTree whileStatement = statement;

        members.addAll(membersFromStatements(whileStatement));
        members.addAll(conditionValue(whileStatement));
        return members;
    }

    private Set<String> conditionValue(final WhileStatementTree whileStatement) {
        final ExpressionTree condition = whileStatement.condition();
        final Set<String> conditionMembers = membersFrom(condition);
        return conditionMembers;
    }

    private Set<String> membersFromStatements(final WhileStatementTree whileStatement) {
        final Set<String> members = new HashSet<>();
        members.addAll(membersFrom(whileStatement.statement()));
        members.addAll(membersFrom(whileStatement.statement()));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final WhileStatementTree statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        final WhileStatementTree whileStatementTree = statement;
        final ExpressionTree condition = whileStatementTree.condition();
        methods.addAll(methodInvocationsFrom(condition));

        final StatementTree body = whileStatementTree.statement();
        methods.addAll(methodInvocationsFrom(body));
        return methods;
    }
}
