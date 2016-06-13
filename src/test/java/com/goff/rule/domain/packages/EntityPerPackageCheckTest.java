package com.goff.rule.domain.packages;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import com.goff.rule.domain.packages.EntityPerPackageCheck;
import com.goff.rule.domain.packages.Package;

public class EntityPerPackageCheckTest {

    @After
    public void clean() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        final EntityPerPackageCheck check = new EntityPerPackageCheck();
        final Field expression = EntityPerPackageCheck.class.getDeclaredField("PACKAGES");
        expression.setAccessible(true);
        final Set<Package> pkgs = (Set<Package>) expression.get(check);
        pkgs.clear();
    }

    @Test
    public void givenTwoEntitiesOfTheSamePackage_shallHasOneIssue() {
        final EntityPerPackageCheck check = new EntityPerPackageCheck();
        check.allowedAmountOfEntities = 1;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/EntityOne.java", check);
        JavaCheckVerifier.verify("src/test/filesSamplesToEvaluate/packages/EntityTwo.java", check);
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/EntityOneInPackageOne.java",
                check);
    }

    @Test
    public void givenPackageOneEntity_shallHasNoIssue() {
        final EntityPerPackageCheck check = new EntityPerPackageCheck();
        check.allowedAmountOfEntities = 1;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/EntityOne.java", check);
    }

    @Test
    public void givenPackageWithoutEntity_shallHasNoIssue() {
        final EntityPerPackageCheck check = new EntityPerPackageCheck();
        check.allowedAmountOfEntities = 1;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/NoEntity.java", check);
    }

    @Test
    public void givenPackageWithOneEntityAndSubPackageWithOneMore_shallHasNoIssue() {
        final EntityPerPackageCheck check = new EntityPerPackageCheck();
        check.allowedAmountOfEntities = 1;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/EntityOne.java", check);
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/EntityOneInPackageOne.java",
                check);
    }

    @Test
    public void givenOneEntityAndOneNoEntity_shallNotHasAnIssue() {
        final EntityPerPackageCheck check = new EntityPerPackageCheck();
        check.allowedAmountOfEntities = 1;

        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/EntityOne.java", check);
        JavaCheckVerifier.verifyNoIssue("src/test/filesSamplesToEvaluate/packages/NoEntity.java", check);
    }
}
