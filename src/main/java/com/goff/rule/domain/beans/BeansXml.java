package com.goff.rule.domain.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.goff.rule.infra.marshaling.Unmashalling;
import com.goff.rule.infra.reader.PropertyFile;

@XmlRootElement(name = "beans", namespace = "http://java.sun.com/xml/ns/javaee")
public class BeansXml {

    private static final String BEANS_XML_PATH = "src/main/webapp/WEB-INF/beans.xml";

    private final String beansXmlPath;

    private Alternative alternatives = new Alternative();

    public BeansXml() {
        this(BEANS_XML_PATH);
    }

    public BeansXml(final String beansXmlPath) {
        this.beansXmlPath = beansXmlPath;
    }

    List<String> checkRequiredAlternatives(final String... requiredAlternatives) {

        final List<String> missingRequiredProperties = new ArrayList<>();

        for (final String requiredProperty : requiredAlternatives)
            if (requiredPropertyNotFound(requiredProperty))
                missingRequiredProperties.add(requiredProperty);

        return missingRequiredProperties;

    }

    private boolean requiredPropertyNotFound(final String requiredProperty) {
        final Set<String> alternativesClasses = findAllternatives();
        final boolean requiredPropertyFound = alternativesClasses.contains(requiredProperty);
        return !requiredPropertyFound;
    }

    Set<String> findAllternatives() {
        final String beansXmlString = PropertyFile.getStringFormatFromFile(beansXmlPath);
        final BeansXml beansXml = Unmashalling.unmarshall(beansXmlString, BeansXml.class);
        return beansXml.alternatives.getQualifiedClass();
    }

    @XmlElement(name = "alternatives", namespace = "http://java.sun.com/xml/ns/javaee")
    public Alternative getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(final Alternative alternatives) {
        this.alternatives = alternatives;
    }

}
