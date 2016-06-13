package com.goff.rule.infra.dynamic.classreader;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class DynamicClassFounderTest {

    @Test
    public void givenPackage_shallReturnSetWithAllClassesInsideIt() {
        final Set<Class<?>> classes = DynamicClassFounder.readClassesFrom("src/test/java/",
                DynamicClassFounderTest.class.getPackage());
        Assert.assertTrue(classes.contains(DynamicClassFounderTest.class));
    }

}
