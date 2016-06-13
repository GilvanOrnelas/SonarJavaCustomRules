package com.goff.rule.infra.tree;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

public abstract class TreeStatement<T extends Tree> {

    public static <T extends Tree> Set<MethodInvocationTree> methodInvocationsFrom(final T statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        final TreeStatement<T> statementReader = TreeRegistry.create(statement);
        final Set<MethodInvocationTree> methodsValues = statementReader.methodInvocationFrom(statement);

        methods.addAll(methodsValues);

        return methods;
    }

    public static <T extends Tree> Set<String> fieldsFromTheMethodTo(final T statement) {
        final Set<String> members = new HashSet<>();
        members.addAll(membersFrom(statement));
        return members;
    }

    protected static <T extends Tree> Set<String> membersFrom(final T statement) {
        final Set<String> members = new HashSet<>();
        final TreeStatement<T> statementReader = TreeRegistry.create(statement);
        final Set<String> membersValues = statementReader.stringValueFromAllMembers(statement);

        members.addAll(membersValues);

        return members;
    }

    protected abstract Set<String> stringValueFromAllMembers(final T statement);

    protected abstract Set<MethodInvocationTree> methodInvocationFrom(final T statement);
}
