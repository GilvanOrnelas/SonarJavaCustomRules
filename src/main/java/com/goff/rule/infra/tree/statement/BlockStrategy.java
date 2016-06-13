package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;

import com.goff.rule.infra.tree.TreeStatement;

public class BlockStrategy extends TreeStatement<BlockTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final BlockTree blockStatement) {
        final Set<String> members = new HashSet<>();

        final List<StatementTree> blockBodyElements = blockStatement.body();
        for (final StatementTree st : blockBodyElements)
            members.addAll(membersFrom(st));

        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final BlockTree blockStatement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        final List<StatementTree> blockBodyElements = blockStatement.body();
        for (final StatementTree stat : blockBodyElements)
            methods.addAll(methodInvocationsFrom(stat));

        return methods;
    }

}
