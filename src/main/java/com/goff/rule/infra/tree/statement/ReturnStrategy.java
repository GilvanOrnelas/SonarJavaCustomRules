package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;

import com.goff.rule.infra.tree.TreeStatement;

public class ReturnStrategy extends TreeStatement<ReturnStatementTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final ReturnStatementTree returnStatement) {
        final ExpressionTree expression = returnStatement.expression();
        if (expression == null)
            return Collections.emptySet();

        final Set<String> members = membersFromTheReturn(expression);
        return members;
    }

    private Set<String> membersFromTheReturn(final ExpressionTree expression) {
        final Set<String> members = new HashSet<>();

        members.add(simpleField(expression));
        members.addAll(membersFromReturnMethod(expression));
        return members;
    }

    private Set<String> membersFromReturnMethod(final ExpressionTree methodInvocation) {
        final Set<String> members = new HashSet<>();
        members.addAll(membersFrom(methodInvocation));
        return members;
    }

    private String simpleField(final ExpressionTree expression) {
        return expression.toString();

    }

    @Override
    public Set<MethodInvocationTree> methodInvocationFrom(final ReturnStatementTree statement) {
        return Collections.emptySet();
    }
}
