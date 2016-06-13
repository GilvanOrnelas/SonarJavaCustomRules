package com.goff.rule.domain.beans;

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.PackageDeclarationTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "BeansXmlTestCheck", name = Constante.PREFIX
        + "Valida o arquivo \"beans.xml\" de teste.", description = "Valida os alternatives no arquivo \"beans.xml\" de teste.", tags = {
                "GOFF", "java" }, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("30min")
public class BeansXmlTestCheck extends SonarBaseTreeRule {

    private static final String BEANS_XML_TEST_PATH = "src/test/resources/META-INF/beans.xml";

    /**
     * O plugin não possui um método para visitar o projeto apenas uma vez, ou
     * verificar arquivos diferentes de .java. Foi utilizado um método de visita
     * qualquer do plugin, e uma API diferente que permite verificar qualquer
     * arquivo. Esse campo faz com que essa verificação seja feita apenas uma
     * vez por análise.
     */
    private static boolean isFirstApplictionAccess = true;

    @RuleProperty(description = "Os valores dos \"alternatives\" obrigatórios. \nExemplo:\n"
            + "com.goff.security.TestAuthentication", defaultValue = "com.goff.security.TestAuthentication")
    protected String requiredAlternatives = "";

    @Override
    public void visitPackage(final PackageDeclarationTree tree) {
        if (!isFirstApplictionAccess)
            return;
        isFirstApplictionAccess = false;

        final List<String> missingAlternatives = new BeansXml(BEANS_XML_TEST_PATH)
                .checkRequiredAlternatives(separatedAlternatives());

        final boolean someAlternativeIsMissing = !missingAlternatives.isEmpty();
        if (someAlternativeIsMissing)
            addGenericIssue("Os seguintes alternatives são obrigatórios: " + missingAlternatives);
    }

    private String[] separatedAlternatives() {
        return requiredAlternatives.split(";");
    }

}
