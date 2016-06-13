package com.goff.rule.infra.tree.statement;

import java.util.Collections;
import java.util.Set;

import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;

import com.goff.rule.infra.tree.TreeStatement;

public class NewClassStrategy extends TreeStatement<NewClassTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final NewClassTree newClassTree) {
        return Collections.emptySet();
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final NewClassTree newClassTree) {
        return Collections.emptySet();
    }
}
