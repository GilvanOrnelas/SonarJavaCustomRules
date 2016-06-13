package com.goff.rule.domain.dependency;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class Dependency {

    private String artifactId = "";
    private String groupId = "";
    private String version = "";
    private String scope = "";

    private Dependency() {
        super();
    }

    boolean scopeIsIncorrect(final String requiredScope) {
        return !scope.equals(requiredScope);
    }

    boolean versionIsIncorrect(final String sugestedVersion) {
        return !version.equals(sugestedVersion);
    }

    boolean isInvalid(final Set<String> validOutsidesDependencies) {
        final boolean isInnerDependency = artifactId.startsWith("goff");
        final boolean isOutsideApprovedDependency = validOutsidesDependencies.contains(artifactId);

        return !(isInnerDependency || isOutsideApprovedDependency);
    }

    boolean exists() {
        return !artifactId.isEmpty();
    }

    static Dependency nullObject() {
        return new Dependency();
    }

    @Override
    public String toString() {
        return "GroupId: " + groupId + " ArtifactId: " + artifactId;
    }

    @XmlElement
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    @XmlElement
    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(final String artifactId) {
        this.artifactId = artifactId;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @XmlElement
    public String getScope() {
        return scope;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

}
