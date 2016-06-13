package com.goff.rule.domain.test_properties;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.reader.PropertyFile;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "TestPropertiesCheck", name = Constante.PREFIX
        + "Valida as propriedades de teste.", description = "Valida as propriedades no arquivo \"test.properties\".", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1h")
public class TestPropertiesCheck extends SonarBaseTreeRule {

    private static final String TEST_PROPERTY_PATH = "src/test/resources/test.properties";

    /**
     * O plugin não possui um método para visitar o projeto apenas uma vez, ou
     * verificar arquivos diferentes de .java. Foi utilizado um método de visita
     * qualquer do plugin, e uma API diferente que permite verificar qualquer
     * arquivo. Esse campo faz com que essa verificação seja feita apenas uma
     * vez por análise.
     */
    private static boolean isFirstApplictionAccess = true;

    @RuleProperty(description = "Propriedades e valores obrigatórios para as propriedades de teste. \nExemplo:\n"
            + "serverAdress=http://localhost:8080/teste/rest,"
            + "#jbossHome=/home/desenvolvedor/servers/jboss-eap-6.2", defaultValue = "serverAdress=http://localhost:8080/teste/rest;"
                    + "#jbossHome=/home/desenvolvedor/servers/jboss-eap-6.2")
    protected String propertiesAndValues = "";

    @Override
    public void visitCompilationUnit(final CompilationUnitTree tree) {
        if (!isFirstApplictionAccess)
            return;
        isFirstApplictionAccess = false;

        verifyMissingProperties();
    }

    private void verifyMissingProperties() {
        final List<String> missingRequiredProperties = checkRequiredProperties();

        final boolean somePropertyIsMissing = !missingRequiredProperties.isEmpty();
        if (somePropertyIsMissing)
            addGenericIssue("As seguintes propriedades e valores são obrigatórias no arquivo test.properties: "
                    + missingRequiredProperties);
    }

    private List<String> checkRequiredProperties() {
        final List<String> properties = PropertyFile.getListStringFormatFromFile(TEST_PROPERTY_PATH);

        final List<String> missingRequiredProperties = new ArrayList<>();

        final String[] requiredProperties = propertiesAndValues.split(";");
        for (final String requiredProperty : requiredProperties)
            if (requiredPropertyNotFound(properties, requiredProperty))
                missingRequiredProperties.add(requiredProperty);

        return missingRequiredProperties;

    }

    private boolean requiredPropertyNotFound(final List<String> properties, final String requiredProperties) {
        return !properties.contains(requiredProperties);
    }

}
