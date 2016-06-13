package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.ListTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.goff.rule.infra.tree.TreeStatement;

public class TryStrategy extends TreeStatement<TryStatementTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final TryStatementTree statement) {
        final Set<String> members = new HashSet<>();

        members.addAll(membersFrom(statement.block()));
        members.addAll(membersFromCatch(statement.catches()));
        members.addAll(membersFromResources(statement.resources()));

        return members;
    }

    private Set<String> membersFromResources(final ListTree<VariableTree> resources) {
        final Set<String> members = new HashSet<>();
        if (resources != null)
            for (final VariableTree variable : resources)
                members.addAll(membersFrom(variable));

        return members;
    }

    private Set<String> membersFromCatch(final List<CatchTree> catches) {
        final Set<String> members = new HashSet<>();
        for (final CatchTree catchBlock : catches)
            members.addAll(membersFrom(catchBlock));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final TryStatementTree statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        return methods;
    }
}
