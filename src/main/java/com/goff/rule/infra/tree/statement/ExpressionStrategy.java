package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public class ExpressionStrategy extends TreeStatement<ExpressionStatementTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final ExpressionStatementTree expressionStatement) {
        final Set<String> members = new HashSet<>();

        members.addAll(membersFrom(expressionStatement.expression()));

        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final ExpressionStatementTree expressionStatement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        methods.addAll(methodInvocationsFrom(expressionStatement.expression()));
        return methods;
    }
}
