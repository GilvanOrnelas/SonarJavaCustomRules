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

@Rule(key = "StatlessAnnotationRequired", name = Constante.PREFIX
        + "Anotação Stateless é obrigatória.", description = "Classes da camada <b>application</b> service devem possuir a anotação Statless.", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1min")
public class StatelessRequiredCheck extends SonarBaseTreeRule {

    private static final String ANNOTATION_NAME = "Stateless";

    @Override
    public void visitClass(final ClassTree classTree) {
        boolean expectedAnnotationExists = true;
        if (PackageVerifier.isInPackage("application", classTree))
            expectedAnnotationExists = AnnotationVerifier.annotationExists(ANNOTATION_NAME, classTree);

        if (!expectedAnnotationExists)
            context.addIssue(classTree, this, "A anotação Stateless é obrigatória para classes da camada application.");

    }

}
