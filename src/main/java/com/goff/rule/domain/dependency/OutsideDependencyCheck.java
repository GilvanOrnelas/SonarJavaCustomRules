package com.goff.rule.domain.dependency;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "OutsideDependencyCheck", name = Constante.PREFIX
        + "Validador de dependências de terceiros.", description = "Verifica se as dependências de terceiros são aprovadas.", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("2h")
public class OutsideDependencyCheck extends SonarBaseTreeRule {

    /**
     * O plugin não possui um método para visitar o projeto apenas uma vez, ou
     * verificar arquivos diferentes de .java. Foi utilizado um método de visita
     * qualquer do plugin, e uma API diferente que permite verificar qualquer
     * arquivo. Esse campo faz com que essa verificação seja feita apenas uma
     * vez por análise.
     */
    private static boolean isFirstApplictionAccess = true;

    @RuleProperty(description = "ArtifactId das dependências externas que são aceitas. \nExemplo:\n"
            + "joda-time; commons-collections", defaultValue = "joda-time")
    protected String allowedOutsidesDependencies = "";

    @Override
    public void visitCompilationUnit(final CompilationUnitTree tree) {
        if (!isFirstApplictionAccess)
            return;
        isFirstApplictionAccess = false;

        verifyInvalidDependencies();
    }

    private void verifyInvalidDependencies() {

        final Set<String> invalidDependencies = Dependencies
                .invalidOutsidesDependencies(splitDependenciesInArtifactIds());

        final boolean thereAreInvalidDependencies = !invalidDependencies.isEmpty();

        if (thereAreInvalidDependencies)
            addGenericIssue("As seguintes dependências, no arquivo pom.xml, são inválidas:" + invalidDependencies);
    }

    private Set<String> splitDependenciesInArtifactIds() {
        return new HashSet<>(Arrays.asList(allowedOutsidesDependencies.split(";")));
    }
}
