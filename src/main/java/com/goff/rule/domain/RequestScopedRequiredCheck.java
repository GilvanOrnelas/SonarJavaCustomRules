package com.goff.rule.domain;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.infra.annotation.AnnotationVerifier;
import com.goff.rule.infra.packages.PackageVerifier;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "RequestScopedRequired", name = Constante.PREFIX
        + "Anotação RequestScoped é obrigatória.", description = "Classes da camada <b>REST</b> devem possuir a anotação @RequestScoped.", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1min")
public class RequestScopedRequiredCheck extends SonarBaseTreeRule {

    private static final String ANNOTATION_NAME = "RequestScoped";

    @Override
    public void visitClass(final ClassTree tree) {
        boolean expectedAnnotationExists = true;
        if (PackageVerifier.isInPackage("rest", tree))
            expectedAnnotationExists = AnnotationVerifier.annotationExists(ANNOTATION_NAME, tree);

        if (!expectedAnnotationExists)
            context.addIssue(tree, this, "A anotação RequestScoped é obrigatória para classes da camada REST.");
    }

}
