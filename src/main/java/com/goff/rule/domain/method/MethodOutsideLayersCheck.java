package com.goff.rule.domain.method;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.packages.PackageVerifier;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "MethodOutsideLayersCheck", name = Constante.PREFIX
        + "Verifica a existência de métodos fora das camadas definidas.", description = "Verifica a existência de métodos fora das camadas definidas.", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("30min")
public class MethodOutsideLayersCheck extends SonarBaseTreeRule {

    @RuleProperty(description = "Camadas disponíveis para o projeto.", defaultValue = "infrastructure;domain;application;rest")
    protected String allowedLayers = "";

    @Override
    public void visitMethod(final MethodTree tree) {
        if (tree.is(Kind.METHOD))
            verifyMethodInsideNotAllowedLayer(tree);
    }

    private void verifyMethodInsideNotAllowedLayer(final MethodTree tree) {
        final boolean notInsideAllowedPackage = !insideAllowedPackages(tree);

        if (notInsideAllowedPackage)
            addIssue(tree, "Não deve haver comportamento (método) fora das camadas definidas (" + allowedLayers + ").");
    }

    private boolean insideAllowedPackages(final MethodTree tree) {
        final ClassTree methodClass = parentClassFrom(tree);
        final String[] packages = allowedLayers.split(";");

        final boolean notInsideAllowedPackage = PackageVerifier.isInPackages(methodClass, packages);
        return notInsideAllowedPackage;
    }

    private ClassTree parentClassFrom(final Tree tree) {
        final Tree parent = tree.parent();

        final boolean isInnerClass = parent instanceof ClassTree;
        if (isInnerClass)
            return parentClassFrom(parent);

        final ClassTree methodClass = (ClassTree) tree;
        return methodClass;
    }
}
