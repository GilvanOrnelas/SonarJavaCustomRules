package com.goff.rule.domain;

import java.util.List;
import java.util.Map;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.infra.reader.MapConverter;
import com.goff.rule.infra.reader.PomFile;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "PomPropertiesCheck", name = Constante.PREFIX
        + "Propriedades obrigatórias do pom.xml.", description = "Propriedades obrigatórias do pom.xml.", tags = {
                "GOFF", "java" }, priority = Priority.MINOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("5min")
public class PomPropertiesChecks extends SonarBaseTreeRule {

    /**
     * O plugin não possui um método para visitar o projeto apenas uma vez, ou
     * verificar arquivos diferentes de .java. Foi utilizado um método de visita
     * qualquer do plugin, e uma API diferente que permite verificar qualquer
     * arquivo. Esse campo faz com que essa verificação seja feita apenas uma
     * vez por análise.
     */
    private static boolean isFirstApplictionAccess = true;

    @RuleProperty(description = "Name of the POM property and his value."
            + " \nExemplo:\nmaven.test.skip=true;version=\\$\\{1.0-SNAPSHOT\\}")
    protected String propertiesAndValues = "";

    @Override
    public void visitCompilationUnit(final CompilationUnitTree tree) {
        if (!isFirstApplictionAccess)
            return;

        isFirstApplictionAccess = false;
        verifyPomProperties();
    }

    private void verifyPomProperties() {
        final Map<String, String> propertyAndValue = MapConverter.convertStringToMap(propertiesAndValues);
        final List<String> missingProperties = PomFile.findMissingPropertiesFrom(propertyAndValue);

        final boolean thereAreMissingProperties = !missingProperties.isEmpty();
        if (thereAreMissingProperties)
            addGenericIssue(
                    "O arquivo pom.xml deve possuir as seguintes propriedades: " + missingProperties.toString());
    }

}
