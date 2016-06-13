package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public class AssignmentStrategy extends TreeStatement<AssignmentExpressionTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final AssignmentExpressionTree assignExpression) {
        final Set<String> members = new HashSet<>();

        final String variableName = assignExpression.variable().toString();
        members.add(variableName);
        members.addAll(membersIfAssignByMethod(assignExpression));
        return members;
    }

    protected Set<String> membersIfAssignByMethod(final AssignmentExpressionTree assignExpression) {
        final Set<String> members = new HashSet<>();
        final ExpressionTree expression = assignExpression.expression();
        members.addAll(membersFrom(expression));
        return members;
    }

    @Override
    public Set<MethodInvocationTree> methodInvocationFrom(final AssignmentExpressionTree statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        final ExpressionTree assignmentExpression = statement.expression();
        methods.addAll(methodInvocationsFrom(assignmentExpression));

        return methods;
    }
}
