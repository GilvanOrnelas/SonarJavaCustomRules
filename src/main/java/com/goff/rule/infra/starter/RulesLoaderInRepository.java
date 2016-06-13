package com.goff.rule.infra.starter;

import java.util.Arrays;
import java.util.List;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

public class RulesLoaderInRepository implements CheckRegistrar {

    @Override
    public void register(final RegistrarContext registrarContext) {
        final List<Class<? extends JavaCheck>> rules = Arrays.asList(RulesAvailables.RULES);
        final List<Class<? extends JavaCheck>> testRules = Arrays.asList(RulesAvailables.TEST_RULES);

        registrarContext.registerClassesForRepository(RepositoryOfRulesStarter.REPOSITORY_KEY, rules, testRules);
    }

}
