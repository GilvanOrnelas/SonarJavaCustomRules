package com.goff.rule.domain.single_responsibility;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.method.Method;
import com.goff.rule.infra.packages.PackageVerifier;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "SingleResponsibilityPrincipleCheck", name = Constante.PREFIX
        + "Variáveis de instância devem ser utilizadas por todos os métodos da classe.", description = "Todos os campos de classe devem ser utilizados por todos os métodos não privados."
                + "Variáveis de instância que são utilizadas por apenas parte dos métodos de uma classe podem ser "
                + "refatoradas para uma classe separada, junto com os métodos que as utilizam. "
                + "Essa prática aproxima o código do princípio da responsabilidade única, "
                + "facilitando o entendimento, manutenções e evoluções.\nÉ sugeria a criação de pacotes para agrupar "
                + "classes de uma mesma funcionalidade. Por exemplo, na camada REST, temos o EscolaRest, que é um CRUD"
                + " de Escola, o mesmo pode ser quebrado nas classes SalvarEscolaRest, DeletarEscolaRest e"
                + " AlterarEscolaRest, todas dentro do novo pacote chamado escola.", tags = { "GOFF",
                        "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1h30min")
public class SingleResponsibilityPrincipleCheck extends SonarBaseTreeRule {

    @RuleProperty(description = "Camadas nas quais essa regra será cobradas.", defaultValue = "application;rest")
    protected String layersToEvaluate = "";

    private final Set<String> fields = new HashSet<>();
    private final Set<Method> methods = new HashSet<>();

    @Override
    public void visitClass(final ClassTree tree) {
        if (PackageVerifier.isInPackages(tree, layersToEvaluate.split(";")))
            verifyMissingFields(tree);

        cleanFieldsAndMethods();
    }

    private void verifyMissingFields(final ClassTree tree) {
        findMethodsAndFields(tree);

        final Set<String> methodsWithMissingFields = methodsWithMissingFields();
        final boolean fieldIsMissingInSomeMethod = !methodsWithMissingFields.isEmpty();
        if (fieldIsMissingInSomeMethod)
            addIssue(tree, String.format("Os seguintes métodos não utilizam os respectivos campos: %s",
                    methodsWithMissingFields));
    }

    private Set<String> methodsWithMissingFields() {
        final Set<String> methodsWithMissingFields = new HashSet<>();

        for (final Method method : methods)
            methodsWithMissingFields.addAll(methodsWithTheirMissingFields(method));

        return methodsWithMissingFields;
    }

    private Set<String> methodsWithTheirMissingFields(final Method method) {
        final Set<String> fieldsNotUsed = method.fieldsNotUsedByMethod(fields, methods);
        final boolean someFieldIsNotUsed = !fieldsNotUsed.isEmpty();

        if (method.isNotPrivate() && someFieldIsNotUsed)
            return fieldsNotUsed;

        return Collections.emptySet();
    }

    private void findMethodsAndFields(final ClassTree classTree) {
        final List<Tree> members = classTree.members();

        for (final Tree member : members)
            defineMemberType(member);
    }

    private void defineMemberType(final Tree member) {
        if (Member.isAField(member) && Member.isNotAConstant(member))
            fields.add(Member.fields(member));
        if (Member.isAMethod(member) && Member.methodIsNotAbstract(member))
            methods.add(Member.methods(member));
    }

    private void cleanFieldsAndMethods() {
        fields.clear();
        methods.clear();
    }
}
