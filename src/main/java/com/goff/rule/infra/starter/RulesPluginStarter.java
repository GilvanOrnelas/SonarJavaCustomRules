package com.goff.rule.infra.starter;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

public class RulesPluginStarter extends SonarPlugin {

    @Override
    public List<Class<? extends Extension>> getExtensions() {
        return Arrays.asList(RepositoryOfRulesStarter.class, RulesLoaderInRepository.class);
    }
}
