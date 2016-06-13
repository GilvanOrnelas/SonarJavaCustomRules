package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public class CatchStrategy extends TreeStatement<CatchTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final CatchTree statement) {
        final Set<String> members = new HashSet<>();
        members.addAll(membersFrom(statement.block()));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final CatchTree statement) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        return methods;
    }
}
