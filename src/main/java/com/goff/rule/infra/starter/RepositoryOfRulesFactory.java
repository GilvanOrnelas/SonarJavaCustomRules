package com.goff.rule.infra.starter;

import java.util.List;

import org.sonar.plugins.java.api.JavaCheck;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public final class RepositoryOfRulesFactory {

    private RepositoryOfRulesFactory() {}

    @SuppressWarnings("rawtypes")
    public static List<Class> getRepository() {
        final Builder<Class> repository = ImmutableList.<Class> builder();
        return repository.addAll(javaRepository()).addAll(javaTestRepository()).build();
    }

    private static List<Class<? extends JavaCheck>> javaRepository() {
        final Builder<Class<? extends JavaCheck>> repository = ImmutableList.<Class<? extends JavaCheck>> builder();
        repository.add(RulesAvailables.RULES);
        return repository.build();
    }

    private static List<Class<? extends JavaCheck>> javaTestRepository() {
        final Builder<Class<? extends JavaCheck>> repository = ImmutableList.<Class<? extends JavaCheck>> builder();
        repository.add(RulesAvailables.TEST_RULES);
        return repository.build();
    }
}
