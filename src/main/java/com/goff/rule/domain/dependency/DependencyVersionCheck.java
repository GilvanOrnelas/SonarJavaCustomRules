package com.goff.rule.domain.dependency;

import java.util.Map;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.reader.MapConverter;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "DependencyVersionCheck", name = Constante.PREFIX
        + "Verifica se determinadas dependências estão na versão esperada.", description = "Verifica se determinadas dependências estão na versão esperada.", tags = {
                "GOFF", "java" }, priority = Priority.INFO)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("10min")
public class DependencyVersionCheck extends SonarBaseTreeRule {

    /**
     * O plugin não possui um método para visitar o projeto apenas uma vez, ou
     * verificar arquivos diferentes de .java. Foi utilizado um método de visita
     * qualquer do plugin, e uma API diferente que permite verificar qualquer
     * arquivo. Esse campo faz com que essa verificação seja feita apenas uma
     * vez por análise.
     */
    private static boolean isFirstApplictionAccess = true;

    @RuleProperty(description = "ArtifactId e versão das dependências que serão verificadas.", defaultValue = "infrastructure=6.0.0")
    protected String dependenciesAndVersions;

    @Override
    public void visitCompilationUnit(final CompilationUnitTree tree) {
        if (!isFirstApplictionAccess)
            return;
        isFirstApplictionAccess = false;

        verifyDependenciesWithWrongScope();
    }

    private void verifyDependenciesWithWrongScope() {
        final Set<String> wrongVersionsDependencies = verifyVersion();

        final boolean someDependencyIsWithWrongVersion = !wrongVersionsDependencies.isEmpty();
        if (someDependencyIsWithWrongVersion)
            addGenericIssue("As seguintes dependências, no arquivo pom.xml, estão no escopo incorreto:"
                    + wrongVersionsDependencies);
    }

    private Set<String> verifyVersion() {
        final Map<String, String> dependencyAndVersion = MapConverter.convertStringToMap(dependenciesAndVersions);

        final Set<String> dependenciesInWrongScope = Dependencies.verifyVersion(dependencyAndVersion);

        return dependenciesInWrongScope;
    }

}
