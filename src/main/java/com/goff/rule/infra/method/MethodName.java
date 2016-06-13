package com.goff.rule.infra.method;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;

import com.goff.rule.infra.tree.TreeStatement;

final class MethodName {

    private MethodName() {
        super();
    }

    public static Set<String> fromMethodAndInnerMethods(final StatementTree statement) {
        final Set<String> methodsNames = new HashSet<>();
        if (Statements.containsOrIsMethod(statement))
            methodsNames.addAll(findMethodName(statement));

        return methodsNames;
    }

    private static Set<String> findMethodName(final StatementTree statement) {
        final Set<String> methodsNames = new HashSet<>();
        final Set<MethodInvocationTree> methods = TreeStatement.methodInvocationsFrom(statement);

        for (final MethodInvocationTree expression : methods)
            methodsNames.addAll(allMethodsNames(expression));

        return methodsNames;
    }

    private static Set<String> allMethodsNames(final MethodInvocationTree expression) {
        final Set<String> methodsNames = new HashSet<>();
        methodsNames.add(methodName(expression));
        methodsNames.addAll(findInnerMethods(expression));
        return methodsNames;
    }

    private static String methodName(final MethodInvocationTree statement) {

        final ExpressionTree methodSelect = statement.methodSelect();
        final boolean isMethodUsingSomeBehavior = methodSelect instanceof MemberSelectExpressionTree;
        if (isMethodUsingSomeBehavior)
            return usedMethod(methodSelect);

        return methodSelect.toString();
    }

    private static String usedMethod(final ExpressionTree methodSelect) {
        return ((MemberSelectExpressionTree) methodSelect).expression().toString();
    }

    private static Set<String> findInnerMethods(final MethodInvocationTree expression) {
        final MethodTree methodTree = (MethodTree) expression.symbol().declaration();
        if (methodHasBodyAndIsNotAbstract(methodTree))
            return Collections.emptySet();

        final Set<String> innerMethods = innerMethods(methodTree);
        return innerMethods;
    }

    private static Set<String> innerMethods(final MethodTree methodTree) {
        final Set<String> innerMethods = new HashSet<>();
        final List<StatementTree> body = methodTree.block().body();
        for (final StatementTree st : body)
            innerMethods.addAll(MethodName.fromMethodAndInnerMethods(st));
        return innerMethods;
    }

    private static boolean methodHasBodyAndIsNotAbstract(final MethodTree methodTree) {
        return (methodTree == null) || (methodTree.block() == null);
    }

}
