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

@Rule(key = "DependencyScopeCheck", name = Constante.PREFIX
        + "Verifica se determinadas dependências estão no escopo correto.", description = "Verifica se determinadas dependências estão no escopo correto.", tags = {
                "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1h")
public class DependencyScopeCheck extends SonarBaseTreeRule {

    /**
     * O plugin não possui um método para visitar o projeto apenas uma vez, ou
     * verificar arquivos diferentes de .java. Foi utilizado um método de visita
     * qualquer do plugin, e uma API diferente que permite verificar qualquer
     * arquivo. Esse campo faz com que essa verificação seja feita apenas uma
     * vez por análise.
     */
    private static boolean isFirstApplictionAccess = true;

    @RuleProperty(description = "ArtifactId e scopo das dependências que serão verificadas.", defaultValue = "test-infrastructure=test")
    protected String dependenciesAndScopes;

    @Override
    public void visitCompilationUnit(final CompilationUnitTree tree) {
        if (!isFirstApplictionAccess)
            return;
        isFirstApplictionAccess = false;

        verifyDependenciesInWrongScope();
    }

    private void verifyDependenciesInWrongScope() {
        final Set<String> wrongScopeDependencies = verifyScope();

        final boolean someDependencyIsWithWrongScope = !wrongScopeDependencies.isEmpty();
        if (someDependencyIsWithWrongScope)
            addGenericIssue("As seguintes dependências, no arquivo pom.xml, estão no escopo incorreto:"
                    + wrongScopeDependencies);
    }

    private Set<String> verifyScope() {
        final Map<String, String> dependencyAndExpectedScope = MapConverter.convertStringToMap(dependenciesAndScopes);

        final Set<String> dependenciesInWrongScope = Dependencies.areInWrongScope(dependencyAndExpectedScope);

        return dependenciesInWrongScope;
    }

}
