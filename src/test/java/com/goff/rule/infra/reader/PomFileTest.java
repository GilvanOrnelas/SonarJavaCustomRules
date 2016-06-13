package com.goff.rule.infra.reader;

import org.junit.Assert;
import org.junit.Test;

import com.goff.rule.domain.dependency.Dependencies;
import com.goff.rule.domain.dependency.Dependency;

public class PomFileTest {

    @Test
    public void test() {
        final Dependency dependency = Dependencies.findBy("sonar-plugin-api");

        Assert.assertEquals("org.codehaus.sonar", dependency.getGroupId());
        Assert.assertEquals("sonar-plugin-api", dependency.getArtifactId());
    }
}
