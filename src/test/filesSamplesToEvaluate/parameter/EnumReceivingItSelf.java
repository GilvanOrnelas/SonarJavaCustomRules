package com.goff.rule.domain.field;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.goff.rule.infra.method.Methods;

public enum PartOfMethod {

    OTHER_METHOD_CALL(MethodInvocationTree.class) {
        @Override
        void stringValuesFromAllMembers(final StatementTree statement, final Set<String> members) {
            final ExpressionStatementTree expressionStatementTree = expressionStatementTree(statement);
            members.addAll(Methods.fieldsFromMethod(expressionStatementTree));
            members.addAll(Methods.argumentsFromInnerMethods(expressionStatementTree));
        }

    },
    USED_VARIABLE(AssignmentExpressionTree.class) {
        @Override
        void stringValuesFromAllMembers(final StatementTree statement, final Set<String> members) {
            final AssignmentExpressionTree assignExpression =
                    (AssignmentExpressionTree) expressionStatementTree(statement).expression();
            final String variableName = assignExpression.variable().toString();
            members.add(variableName);
        }

    },
    CREATED_VARIABLE(VariableTree.class) {
        @Override
        void stringValuesFromAllMembers(final StatementTree statement, final Set<String> members) {
            final VariableTree variableTree = (VariableTree) statement;
            addVariableName(members, variableTree);
            addVariableValue(members, variableTree);
        }

        private void addVariableName(final Set<String> members, final VariableTree variableTree) {
            final String variableName = variableTree.simpleName().toString();
            members.add(variableName);
        }

        private void addVariableValue(final Set<String> members, final VariableTree variableTree) {
            final ExpressionTree initializer = variableTree.initializer();
            if (initializer != null) {
                final String variableValue = initializer.toString();
                members.add(variableValue);
            }

        }

    };

    private static final Map<Class<? extends Tree>, PartOfMethod> PARTS = new ConcurrentHashMap<>();

    static {
        for (final PartOfMethod part : PartOfMethod.values()) {
            PARTS.put(part.treeStatement, part);
        }
    }
    private Class<? extends Tree> treeStatement;

    private PartOfMethod(final Class<? extends Tree> treeStatement) {
        this.treeStatement = treeStatement;
    }

    static void addFieldsFromTheMethodTo(final Set<String> members, final StatementTree statement) {
        if (statement instanceof ExpressionStatementTree) {
            membersFromExpressionStatement(expressionStatementTree(statement), members);
        }
        if (statement instanceof VariableTree) {
            membersFrom(statement, members, interfaceFrom(statement.getClass()));
        }
    }

    private static void membersFromExpressionStatement(final ExpressionStatementTree statement,
            final Set<String> members) {
        final Class<?> interfaceFromStatement = interfaceFrom(statement.expression().getClass());
        membersFrom(statement, members, interfaceFromStatement);
    }

    private static void membersFrom(final StatementTree statement, final Set<String> members,
            final Class<?> interfaceFromStatement) {
        final PartOfMethod partOfMethod = PARTS.get(interfaceFromStatement);

        if (partOfMethod != null) {
            partOfMethod.stringValuesFromAllMembers(statement, members);
        }
    }

    private static ExpressionStatementTree expressionStatementTree(final StatementTree statement) {
        return (ExpressionStatementTree) statement;
    }

    private static Class<?> interfaceFrom(final Class<?> clazz) {
        return clazz.getInterfaces()[0];
    }

    abstract void stringValuesFromAllMembers(final StatementTree statement, final Set<String> members);
}
