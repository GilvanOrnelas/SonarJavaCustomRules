package com.goff.rule.infra.tree.statement;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.method.Fields;
import com.goff.rule.infra.method.InnerMethodArguments;
import com.goff.rule.infra.tree.TreeStatement;

public class MethodInvocationStrategy extends TreeStatement<MethodInvocationTree> {

    @Override
    protected Set<String> stringValueFromAllMembers(final MethodInvocationTree methodInvocation) {
        final Set<String> members = new HashSet<>();
        final Set<String> methodNameAndArguments = methodNameAndArguments(methodInvocation);
        members.addAll(methodNameAndArguments);
        return members;
    }

    private Set<String> methodNameAndArguments(final MethodInvocationTree methodInvocation) {
        final Set<String> members = new HashSet<>();
        final ExpressionTree methodSelect = methodInvocation.methodSelect();
        members.addAll(membersFrom(methodSelect));
        members.addAll(Fields.findAllFrom(methodInvocation));
        members.addAll(InnerMethodArguments.findAllFrom(methodInvocation));
        return members;
    }

    @Override
    protected Set<MethodInvocationTree> methodInvocationFrom(final MethodInvocationTree methodInvocation) {
        final Set<MethodInvocationTree> methods = new HashSet<>();

        methods.add(methodInvocation);

        return methods;
    }
}
