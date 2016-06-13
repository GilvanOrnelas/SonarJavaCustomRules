package com.goff.rule.domain.packages;

import java.util.HashSet;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.goff.rule.domain.Constante;
import com.goff.rule.infra.annotation.AnnotationVerifier;
import com.goff.rule.infra.packages.PackageVerifier;
import com.goff.rule.infra.sonar.SonarBaseTreeRule;

@Rule(key = "EntityPerPackageCheck", name = Constante.PREFIX
        + "Verifica a quantidade de Entidades mapeadas por cada pacote.", description = "Verifica a quantidade de Entidades mapeadas por cada pacote.", tags = {
                "GOFF", "java" }, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1h")
public class EntityPerPackageCheck extends SonarBaseTreeRule {

    private static final Set<Package> PACKAGES = new HashSet<>();

    @RuleProperty(description = "Quantidade de entidades permitidas por pacote.", defaultValue = "1")
    protected int allowedAmountOfEntities = 1;

    @Override
    public void visitClass(final ClassTree classTree) {
        defineAmountOfEntitiesPerPackage(classTree);

        final Set<Package> packagesWithIssue = packagesWithTooManyEntities();

        final Set<Package> notYetReported = Package.notYetReported(packagesWithIssue);
        if (!notYetReported.isEmpty())
            addIssue(classTree, "O seguite pacote deve possuir apenas uma entidade: " + notYetReported);
    }

    private void defineAmountOfEntitiesPerPackage(final ClassTree tree) {
        final boolean isInDomain = PackageVerifier.isInPackage("domain", tree);
        final boolean isEntity = AnnotationVerifier.annotationExists("Entity", tree);
        final String packageName = PackageVerifier.packageName(tree);

        createPackageWithoutEntity(packageName);

        if (isInDomain && isEntity)
            increaseEntitiesIn(packageName);
    }

    private void increaseEntitiesIn(final String packageName) {
        for (final Package pkg : PACKAGES)
            if (pkg.equals(packageName))
                pkg.increaseAmountOfEntities();
    }

    private Set<Package> packagesWithTooManyEntities() {
        final Set<Package> packagesWithTooManyEntities = new HashSet<>();
        for (final Package pkg : PACKAGES)
            if (pkg.hasMoreEntitiesThanThe(allowedAmountOfEntities))
                packagesWithTooManyEntities.add(pkg);
        return packagesWithTooManyEntities;
    }

    private void createPackageWithoutEntity(final String packageName) {
        if (!PACKAGES.contains(packageName))
            PACKAGES.add(new Package(packageName));
    }

}
