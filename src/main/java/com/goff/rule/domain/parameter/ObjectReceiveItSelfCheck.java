package com.goff.rule.domain.parameter;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "ObjectReceiveItSelfCheck", name = Constante.PREFIX
        + "Objetos não devem receber eles mesmos como parâmetro.", description = "Objetos não devem receber eles mesmos como parâmetro.", tags = {
                "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("30min")
public class ObjectReceiveItSelfCheck extends SonarBaseTreeRule {

    @Override
    public void visitMethod(final MethodTree tree) {
        final String className = findClassName(tree);

        for (final VariableTree parameter : tree.parameters())
            verifyMethodsWithInvalidParameterType(tree, className, parameter);
    }

    private void verifyMethodsWithInvalidParameterType(final MethodTree tree, final String className,
            final VariableTree parameter) {
        final TypeTree methodParameterType = parameter.type();

        if (methodReceivesInvalidParameterType(methodParameterType, className))
            addIssue(tree, "Objetos não devem receber eles mesmos como parâmetros.");
    }

    private boolean methodReceivesInvalidParameterType(final TypeTree methodParameterType, final String className) {
        final String methodParameterName = methodParameterType.toString();
        return methodParameterName.equals(className);
    }

    private String findClassName(final MethodTree tree) {
        final ClassTree methodsClass = (ClassTree) tree.parent();
        final String className = className(methodsClass);
        return className;
    }

    private String className(final ClassTree methodsClass) {
        final boolean isEnum = methodsClass.simpleName() == null;
        if (isEnum)
            return "enum";
        return methodsClass.simpleName().toString();
    }

}
