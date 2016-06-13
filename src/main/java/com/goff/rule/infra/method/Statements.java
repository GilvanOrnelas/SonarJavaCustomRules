package com.goff.rule.infra.method;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;

final class Statements {

    private static final Set<Class<? extends StatementTree>> STATEMENTS_WITH_METHODS_INSIDE = statementsWithMethodsInside();

    private Statements() {
        super();
    }

    private static Set<Class<? extends StatementTree>> statementsWithMethodsInside() {
        final Set<Class<? extends StatementTree>> st = new HashSet<>();
        st.add(IfStatementTree.class);
        st.add(ForEachStatement.class);
        st.add(WhileStatementTree.class);
        st.add(ForStatementTree.class);
        return st;
    }

    static boolean containsOrIsMethod(final StatementTree statement) {
        final boolean containsMethod = isStatementWithMethodInside(statement) || assignerIsMethod(statement);
        final boolean isMethod = statement.is(Kind.EXPRESSION_STATEMENT);
        return containsMethod || isMethod;
    }

    static boolean isStatementWithMethodInside(final StatementTree statement) {
        final Class<? extends StatementTree> statementClass = statement.getClass();
        final Class<?>[] interfaces = statementClass.getInterfaces();
        final Class<?> statementInterface = interfaces[0];

        return STATEMENTS_WITH_METHODS_INSIDE.contains(statementInterface);
    }

    static boolean assignerIsMethod(final StatementTree statement) {
        boolean assignerIsMethod = false;
        if (statement instanceof VariableTree)
            assignerIsMethod = assignerIsMethodInvocation(statement);

        return assignerIsMethod;
    }

    private static boolean assignerIsMethodInvocation(final StatementTree statement) {
        final ExpressionTree initializer = ((VariableTree) statement).initializer();
        return (initializer != null) && initializer.is(Kind.METHOD_INVOCATION);
    }
}
