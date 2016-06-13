package com.goff.rule.domain.beans;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alternative {

    private Set<String> qualifiedClass = new HashSet<>();

    public Alternative() {
        super();
    }

    @Override
    public boolean equals(final Object obj) {
        final Alternative alt = (Alternative) obj;
        return qualifiedClass.equals(alt.qualifiedClass);
    }

    @Override
    public int hashCode() {
        return qualifiedClass.hashCode();
    }

    @Override
    public String toString() {
        return qualifiedClass.toString();
    }

    @XmlElement(name = "class", namespace = "http://java.sun.com/xml/ns/javaee")
    public Set<String> getQualifiedClass() {
        return qualifiedClass;
    }

    public void setQualifiedClass(final Set<String> qualifiedClass) {
        this.qualifiedClass = qualifiedClass;
    }

}
