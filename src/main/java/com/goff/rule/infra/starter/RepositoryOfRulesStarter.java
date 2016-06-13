package com.goff.rule.infra.starter;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.java.Java;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

public class RepositoryOfRulesStarter implements RulesDefinition {

    public static final String REPOSITORY_KEY = "goff-java";
    private static final String REPOSITORY_NAME = "GOFF Java";

    @Override
    public void define(final Context context) {
        final NewRepository repository = context.createRepository(REPOSITORY_KEY, Java.KEY);
        repository.setName(REPOSITORY_NAME);

        AnnotationBasedRulesDefinition.load(repository, "java", RepositoryOfRulesFactory.getRepository());
        repository.done();
    }
}
