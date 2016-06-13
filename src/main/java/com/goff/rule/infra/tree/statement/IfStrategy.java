package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;

import com.goff.rule.infra.tree.TreeStatement;

public class IfStrategy extends TreeStatement<IfStatementTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final IfStatementTree statement) {
        final Set<String> members = new HashSet<>();
        final IfStatementTree ifStatement = statement;
        final List<StatementTree> thenStatement = ((BlockTree) ifStatement.thenStatement()).body();
        members.addAll(membersFrom(ifStatement.condition()));
        members.addAll(thenStatementMembers(thenStatement));
        members.addAll(elseStatementMembers(ifStatement));
        return members;
    }

    private Set<String> elseStatementMembers(final IfStatementTree ifStatement) {
        final StatementTree statement = ifStatement.elseStatement();
        if (statement == null)
            return Collections.emptySet();

        if (statement instanceof IfStatementTree)
            return stringValueFromAllMembers((IfStatementTree) statement);

        return thenStatementMembers(elseStatement(statement));
    }

    private List<StatementTree> elseStatement(final StatementTree statement) {
        return ((BlockTree) statement).body();
    }

    private Set<String> thenStatementMembers(final List<StatementTree> statements1) {
        final Set<String> members = new HashSet<>();
        for (final StatementTree statementInBlock1 : statements1)
            members.addAll(membersFrom(statementInBlock1));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final IfStatementTree statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        final StatementTree thenStatement = statement.thenStatement();
        methods.addAll(methodInvocationsFrom(thenStatement));

        return methods;
    }
}
