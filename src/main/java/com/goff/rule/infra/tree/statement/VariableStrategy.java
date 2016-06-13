package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.goff.rule.infra.tree.TreeStatement;

public class VariableStrategy extends TreeStatement<VariableTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final VariableTree variableTree) {
        final Set<String> members = new HashSet<>();
        members.add(variableName(variableTree));
        members.addAll(variableValue(variableTree));
        return members;
    }

    private String variableName(final VariableTree variableTree) {
        final String variableName = variableTree.simpleName().toString();
        return variableName;
    }

    private Set<String> variableValue(final VariableTree variableTree) {
        final ExpressionTree initializer = variableTree.initializer();
        if (initializer != null)
            return membersFrom(initializer);

        return Collections.emptySet();
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final VariableTree statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        final ExpressionTree initializer = statement.initializer();
        final boolean variableIsInitialized = initializer != null;
        if (variableIsInitialized)
            methods.addAll(methodInvocationsFrom(initializer));

        return methods;
    }
}
