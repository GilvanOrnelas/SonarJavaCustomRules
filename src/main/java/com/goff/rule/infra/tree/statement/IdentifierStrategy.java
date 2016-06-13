package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public class IdentifierStrategy extends TreeStatement<IdentifierTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final IdentifierTree identifierTree) {
        final Set<String> members = new HashSet<>();
        members.add(identifierTree.toString());
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final IdentifierTree statement) {
        return Collections.emptySet();
    }

}
