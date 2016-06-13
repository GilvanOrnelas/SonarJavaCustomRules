package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public class MemberSelectStrategy extends TreeStatement<MemberSelectExpressionTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final MemberSelectExpressionTree memberSelect) {
        final Set<String> members = new HashSet<>();

        members.addAll(membersFrom(memberSelect.expression()));

        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final MemberSelectExpressionTree statement) {
        return Collections.emptySet();
    }
}
