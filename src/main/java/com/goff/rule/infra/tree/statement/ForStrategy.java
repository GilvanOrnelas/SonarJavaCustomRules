package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.ListTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;

import com.goff.rule.infra.tree.TreeStatement;

public class ForStrategy extends TreeStatement<ForStatementTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final ForStatementTree forStatement) {
        final Set<String> members = new HashSet<>();
        final Set<String> conditions = membersFrom(forStatement.condition());
        final Set<String> initializers = initializerMembers(forStatement);
        final Set<String> membersFromBody = membersFrom(forStatement.statement());

        members.addAll(conditions);
        members.addAll(initializers);
        members.addAll(membersFromBody);
        return members;
    }

    private Set<String> initializerMembers(final ForStatementTree forStatement) {
        final Set<String> members = new HashSet<>();
        final ListTree<StatementTree> initializers = forStatement.initializer();
        for (final StatementTree variable : initializers)
            members.addAll(membersFrom(variable));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final ForStatementTree forStatement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        methods.addAll(initializerMethods(forStatement));
        methods.addAll(updateMethods(forStatement));
        methods.addAll(methodsInBody(forStatement));

        return methods;
    }

    private Set<MethodInvocationTree> methodsInBody(final ForStatementTree forStatementTree) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        final Set<MethodInvocationTree> methodsInBody = methodInvocationsFrom(forStatementTree.statement());
        methods.addAll(methodsInBody);
        return methods;
    }

    private Set<MethodInvocationTree> initializerMethods(final ForStatementTree forStatementTree) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        final ListTree<StatementTree> initializers = forStatementTree.initializer();
        for (final StatementTree variable : initializers)
            methods.addAll(methodInvocationsFrom(variable));
        return methods;
    }

    private Set<MethodInvocationTree> updateMethods(final ForStatementTree forStatementTree) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        final ListTree<StatementTree> updates = forStatementTree.update();
        for (final StatementTree variable : updates)
            methods.addAll(methodInvocationsFrom(variable));

        return methods;
    }
}
