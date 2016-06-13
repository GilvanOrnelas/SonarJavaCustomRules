package com.goff.rule.infra.method;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;

import com.goff.rule.infra.tree.TreeStatement;

final class Member {

    private Member() {
        super();
    }

    static Set<String> findAll(final MethodTree member) {
        final Set<String> members = new HashSet<>();
        final BlockTree block = member.block();
        final List<StatementTree> body = block.body();

        for (final StatementTree statementTree : body)
            members.addAll(TreeStatement.fieldsFromTheMethodTo(statementTree));

        return members;
    }

}
