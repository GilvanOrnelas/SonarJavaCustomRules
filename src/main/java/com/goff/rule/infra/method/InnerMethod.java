package com.goff.rule.infra.method;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;

final class InnerMethod {

    private InnerMethod() {
        super();
    }

    static Set<String> findAllMethods(final MethodTree member) {
        final Set<String> methods = new HashSet<>();
        final BlockTree block = member.block();
        final List<StatementTree> body = block.body();

        for (final StatementTree method : body)
            methods.addAll(MethodName.fromMethodAndInnerMethods(method));
        return methods;
    }

}
