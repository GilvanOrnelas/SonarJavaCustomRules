package com.goff.rule.infra.variable;

import org.sonar.plugins.java.api.tree.VariableTree;

public final class Variable {

    private Variable() {
        super();
    }

    public static String name(final VariableTree variable) {
        return variable.simpleName().toString();
    }
}
