package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public class BinaryStrategy extends TreeStatement<BinaryExpressionTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final BinaryExpressionTree binaryExpressionTree) {
        final Set<String> members = new HashSet<>();
        members.addAll(membersFrom(binaryExpressionTree.leftOperand()));
        members.addAll(membersFrom(binaryExpressionTree.rightOperand()));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final BinaryExpressionTree binaryExpressionTree) {
        final Set<MethodInvocationTree> methods = new HashSet<>();
        methods.addAll(methodInvocationsFrom(binaryExpressionTree.rightOperand()));
        methods.addAll(methodInvocationsFrom(binaryExpressionTree.leftOperand()));
        return methods;
    }
}
