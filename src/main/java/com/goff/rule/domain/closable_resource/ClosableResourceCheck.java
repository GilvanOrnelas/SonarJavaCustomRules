package com.goff.rule.domain.closable_resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.reflect.PrivateField;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "ClosableResourceCheck", name = Constante.PREFIX
        + "Recursos abertos devem ser fechados utilizando o try-with-resources.", description = "Recursos abertos devem ser fechados utilizando o try-with-resources, e não o bloco finally. "
                + "Exemlpo: Incorreto:\nfinally {stream.close();}\nCorreto:\ntry(InputStream stream = new InputStream();)", tags = {
                        "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("10min")
public class ClosableResourceCheck extends SonarBaseTreeRule {

    @Override
    public void visitTryStatement(final TryStatementTree tree) {
        if (hasFinallyBlock(tree) && closeWasInvoked(tree))
            addIssue(tree, "Recursos do tipo Closable devem ser fechados com o try-with-resources.");
    }

    private boolean closeWasInvoked(final TryStatementTree tree) {
        final Set<String> invokedMethods = invokedMethods(tree);
        return invokedMethods.contains("Closeable");
    }

    private Set<String> invokedMethods(final TryStatementTree tree) {
        final Set<String> invokedMethods = new HashSet<>();
        final List<StatementTree> body = tree.finallyBlock().body();
        for (final StatementTree statement : body)
            invokedMethods.add(typesName(statement));
        return invokedMethods;
    }

    private String typesName(final StatementTree statement) {
        if (statement instanceof ExpressionStatementTree)
            return methodInvocation((ExpressionStatementTree) statement);
        return "";
    }

    private String methodInvocation(final ExpressionStatementTree expressionStatement) {
        final ExpressionTree expression = expressionStatement.expression();
        if (expression instanceof MethodInvocationTree)
            return memberSelect((MethodInvocationTree) expression);
        return "";
    }

    private String memberSelect(final MethodInvocationTree methodInvocation) {

        final ExpressionTree methodSelect = methodInvocation.methodSelect();
        if (methodSelect instanceof MemberSelectExpressionTree) {
            final ExpressionTree expression = ((MemberSelectExpressionTree) methodSelect).expression();
            return identifierTree(expression);
        }
        return "";
    }

    private String identifierTree(final ExpressionTree expression) {
        if (expression instanceof IdentifierTree) {
            final Symbol symbol = ((IdentifierTree) expression).symbol();
            return interfacesNames(symbol);
        }
        return "";
    }

    private String interfacesNames(final Symbol symbol) {
        final Type type = symbol.type();
        final String interfaces = PrivateField.extractValue(type, "interfaces");
        if (interfaces.isEmpty())
            return "";
        final String interfacesNames = interfaces.substring(1, interfaces.length() - 1);
        return interfacesNames;
    }

    private boolean hasFinallyBlock(final TryStatementTree tree) {
        return tree.finallyBlock() != null;
    }
}
