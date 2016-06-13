package com.goff.rule.infra.method;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.Arguments;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import com.goff.rule.infra.tree.TreeStatement;

public final class Fields {

    private Fields() {
        super();
    }

    public static Set<String> findAllFrom(final MethodInvocationTree statement) {
        final Set<String> possibleFields = new HashSet<>();
        final MethodInvocationTree expression = methodInvocationIfAssignmentTree(statement);
        final ExpressionTree methodSelect = expression.methodSelect();

        if (isFieldInvokingMethod(methodSelect))
            possibleFields.addAll(allFields(methodSelect));
        else if (isMethodDirectBeingInvoked(methodSelect))
            possibleFields.addAll(InnerMethodArguments.findAllFrom(statement));

        return possibleFields;
    }

    private static Set<String> allFields(final ExpressionTree methodSelect) {
        final Set<String> possibleFields = new HashSet<>();
        possibleFields.addAll(TreeStatement.fieldsFromTheMethodTo(methodSelect));
        possibleFields.addAll(fieldsCallingMethodsRecursively(methodSelect));
        return possibleFields;
    }

    private static MethodInvocationTree methodInvocationIfAssignmentTree(final MethodInvocationTree statement) {
        if (statement instanceof AssignmentExpressionTree)
            return (MethodInvocationTree) ((AssignmentExpressionTree) statement).expression();

        return statement;
    }

    private static Set<String> fieldsCallingMethodsRecursively(final ExpressionTree methodSelect) {
        final Set<String> possibleFields = new HashSet<>();

        final Arguments arguments = ((MethodInvocationTree) methodSelect.parent()).arguments();
        if (!arguments.isEmpty())
            possibleFields.addAll(TreeStatement.fieldsFromTheMethodTo(arguments.get(0)));

        return possibleFields;
    }

    private static boolean isMethodDirectBeingInvoked(final ExpressionTree methodSelect) {
        return methodSelect instanceof IdentifierTree;
    }

    private static boolean isFieldInvokingMethod(final ExpressionTree methodSelect) {
        return methodSelect instanceof MemberSelectExpressionTree;
    }

}
