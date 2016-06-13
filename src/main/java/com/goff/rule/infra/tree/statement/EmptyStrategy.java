package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.Set;

import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import com.goff.rule.infra.tree.TreeStatement;

public class EmptyStrategy extends TreeStatement<Tree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final Tree statement) {
        return Collections.emptySet();
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final Tree statement) {
        return Collections.emptySet();
    }

}
