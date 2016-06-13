package com.goff.rule.domain.dependency;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.goff.rule.infra.marshaling.Unmashalling;
import com.goff.rule.infra.reader.PomFile;

@XmlRootElement
public class Dependencies {

    private static final Set<Dependency> POM_DEPENDENCIES = findAllDependencies();

    private Set<Dependency> setOfdependencies = new HashSet<>();

    public static Set<String> areInWrongScope(final Map<String, String> dependencyAndExpectedScope) {
        final Set<String> wrongScopeDependencies = new HashSet<>();

        for (final Entry<String, String> dependency : dependencyAndExpectedScope.entrySet())
            wrongScopeDependencies.add(dependenciesInWrongScope(dependency));

        wrongScopeDependencies.remove("");
        return wrongScopeDependencies;
    }

    private static String dependenciesInWrongScope(final Entry<String, String> dependency) {
        final String artifactId = dependency.getKey();
        final Dependency pomDependency = Dependencies.findBy(artifactId);

        final String requiredScope = dependency.getValue();
        if (pomDependency.exists() && pomDependency.scopeIsIncorrect(requiredScope))
            return String.format("%nDependência: \"%s\" Escopo: \"%s\"", artifactId, requiredScope);
        return "";
    }

    public static Set<String> verifyVersion(final Map<String, String> dependencyAndVersion) {
        final Set<String> wrongVersionDependencies = new HashSet<>();

        for (final Entry<String, String> dependency : dependencyAndVersion.entrySet())
            wrongVersionDependencies.add(dependenciesWithWrongVersion(dependency));

        wrongVersionDependencies.remove("");
        return wrongVersionDependencies;
    }

    private static String dependenciesWithWrongVersion(final Entry<String, String> dependency) {
        final String artifactId = dependency.getKey();
        final Dependency pomDependency = Dependencies.findBy(artifactId);

        final String sugestedVersion = dependency.getValue();
        if (pomDependency.exists() && pomDependency.versionIsIncorrect(sugestedVersion))
            return String.format("%nDependência: \"%s\" Versão: \"%s\"", artifactId, sugestedVersion);
        return "";
    }

    public static Dependency findBy(final String artifactId) {
        for (final Dependency d : POM_DEPENDENCIES)
            if (artifactId.equals(d.getArtifactId()))
                return d;
        return Dependency.nullObject();
    }

    public static Set<String> invalidOutsidesDependencies(final Set<String> validOutsidesDependencies) {
        final Set<String> invalidDependencies = new HashSet<>();
        for (final Dependency d : POM_DEPENDENCIES)
            if (d.isInvalid(validOutsidesDependencies))
                invalidDependencies.add(d.getArtifactId());
        return invalidDependencies;
    }

    private static Set<Dependency> findAllDependencies() {
        final String dependenciesXml = PomFile.findDependencies();
        final Dependencies dependencies = Unmashalling.unmarshall(dependenciesXml, Dependencies.class);
        return dependencies.setOfdependencies;
    }

    @XmlElement(name = "dependency")
    Set<Dependency> getDependencies() {
        return setOfdependencies;
    }

    void setDependencies(final Set<Dependency> dependencies) {
        setOfdependencies = dependencies;
    }
}
