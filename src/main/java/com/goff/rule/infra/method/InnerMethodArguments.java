package com.goff.rule.infra.method;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.Arguments;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

public final class InnerMethodArguments {

    private InnerMethodArguments() {
        super();
    }

    public static Set<String> findAllFrom(final MethodInvocationTree statement) {
        final Set<String> arguments = new HashSet<>();
        final Arguments argumentsTree = methodInvocationIfAssignmentTree(statement).arguments();

        for (final ExpressionTree arg : argumentsTree)
            arguments.add(assignerName(arg));
        return arguments;
    }

    private static MethodInvocationTree methodInvocationIfAssignmentTree(final MethodInvocationTree statement) {
        if (statement instanceof AssignmentExpressionTree)
            return (MethodInvocationTree) ((AssignmentExpressionTree) statement).expression();

        return statement;
    }

    private static String assignerName(final ExpressionTree argument) {
        final boolean argumentIsAMethodInvocation = argument instanceof MethodInvocationTree;
        if (argumentIsAMethodInvocation)
            return fieldFromMethodInvocation(argument);
        return argument.toString();
    }

    private static String fieldFromMethodInvocation(final ExpressionTree arg) {
        final MethodInvocationTree methodInvoked = (MethodInvocationTree) arg;
        final ExpressionTree methodSelect = methodInvoked.methodSelect();
        if (methodSelect instanceof MemberSelectExpressionTree)
            return ((MemberSelectExpressionTree) methodSelect).expression().toString();

        return "";
    }

}
