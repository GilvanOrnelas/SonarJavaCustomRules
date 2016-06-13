package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;

import com.goff.rule.infra.tree.TreeStatement;

public class ForEachStrategy extends TreeStatement<ForEachStatement> {

    @Override
    protected Set<String> stringValueFromAllMembers(final ForEachStatement forEachStatement) {
        final Set<String> members = new HashSet<>();

        members.addAll(iteratorName(forEachStatement));
        members.addAll(membersFrom(forEachStatement.statement()));
        return members;
    }

    private Set<String> iteratorName(final ForEachStatement forEachStatement) {
        final Set<String> members = new HashSet<>();

        members.addAll(membersFrom(forEachStatement.expression()));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final ForEachStatement forStatementTree) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        final StatementTree body = forStatementTree.statement();
        methods.addAll(methodInvocationsFrom(body));

        return methods;
    }
}
