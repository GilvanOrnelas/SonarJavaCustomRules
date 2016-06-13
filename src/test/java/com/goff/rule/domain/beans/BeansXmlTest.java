package com.goff.rule.domain.beans;

import java.util.List;
import java.util.Set;

import org.junit.Assert;

import com.goff.rule.domain.beans.BeansXml;

public abstract class BeansXmlTest {
    private final String beansXmlPath;
    private final String alternative;

    public BeansXmlTest(final String beansXmlPath, final String alternative) {
        this.beansXmlPath = beansXmlPath;
        this.alternative = alternative;
    }

    public final void givenBeansXmlFile_shallBeAbleToReadTheAlternatives() {

        final Set<String> alternatives = new BeansXml(beansXmlPath).findAllternatives();

        final String alternative = alternatives.iterator().next();
        Assert.assertEquals(this.alternative, alternative);
    }

    public final void givenAlternativeThatExists_shallNotHaveAnyMissingAlternative() {

        final String[] alternatives = {alternative};

        final List<String> missingRequiredAlternatives =
                new BeansXml(beansXmlPath).checkRequiredAlternatives(alternatives);

        Assert.assertTrue(missingRequiredAlternatives.isEmpty());

    }

}
