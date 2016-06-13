package com.goff.rule.domain.packages;

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.ImportTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.packages.PackageVerifier;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "BelowPackageAccessCheck", name = Constante.PREFIX
        + "Pacotes não devem visualizar seus sub-pacotes.", description = "Pacotes não devem visualizar seus sub-pacotes. Exemplo: O pacote com.goff.domain não deve "
                + "visualizar o pacote com.goff.domain.security.", tags = { "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1h")
public class BelowPackageAccessCheck extends SonarBaseTreeRule {

    @Override
    public void visitImport(final ImportTree tree) {
        final String importName = importName(tree.qualifiedIdentifier());
        final String packageName = packageName(tree);

        if (isInSamePackage(importName, packageName) && isSubPackage(importName, packageName))
            addIssue(tree, "Não é permitido utilizar sub-pacotes do pacote \"" + packageName + "\".");
    }

    private String packageName(final ImportTree tree) {
        final CompilationUnitTree compilationUnit = (CompilationUnitTree) tree.parent();
        final List<Tree> types = compilationUnit.types();
        final ClassTree classWithTheImport = (ClassTree) types.get(0);
        return PackageVerifier.packageName(classWithTheImport);
    }

    private String importName(final Tree qualifiedIdentifier) {
        String completeImportName = importTokenName(qualifiedIdentifier);

        if (qualifiedIdentifier instanceof IdentifierTree)
            completeImportName = String.format("%s%s", qualifiedIdentifier.toString(), completeImportName);
        return completeImportName;
    }

    private String importTokenName(final Tree qualifiedIdentifier) {
        if (qualifiedIdentifier instanceof MemberSelectExpressionTree)
            return fullQualifiedName(qualifiedIdentifier);

        return "";
    }

    private String fullQualifiedName(final Tree qualifiedIdentifier) {
        String completeImportName;
        final MemberSelectExpressionTree memberSelect = (MemberSelectExpressionTree) qualifiedIdentifier;
        final IdentifierTree tokenName = memberSelect.identifier();
        completeImportName = tokenName.toString();
        completeImportName = nextImportTokenName(completeImportName, memberSelect);
        return completeImportName;
    }

    private String nextImportTokenName(final String completeImportName, final MemberSelectExpressionTree memberSelect) {
        final ExpressionTree expression = memberSelect.expression();
        final String importName = importName(expression);
        return String.format("%s.%s", importName, completeImportName);
    }

    private boolean isInSamePackage(final String importName, final String packageName) {
        return importName.contains(packageName);
    }

    private boolean isSubPackage(final String importName, final String packageName) {
        final int importTokensWithoutTheClassName = stringTokens(importName) - 1;
        return importTokensWithoutTheClassName > stringTokens(packageName);
    }

    private int stringTokens(final String importName) {
        return importName.split("\\.").length;
    }
}
