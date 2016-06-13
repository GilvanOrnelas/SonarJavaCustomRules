package com.goff.rule.infra.reader;

public final class PomDependencyTagLine {

    static final String DEPENDENCIES_END_TAG = "</dependencies>";

    private static final String DEPENDENCIES_TAG = "<dependencies>";
    private boolean insideDependencies = false;
    private boolean outsideDependencyManagement = true;

    PomDependencyTagLine() {
        super();
    }

    boolean isFromDependencyTag(final String line) {
        outsideDependencyManagement = isOutsideDependencyManagementTag(line, outsideDependencyManagement);
        insideDependencies = isInsideDependenciesTag(line, insideDependencies);

        return outsideDependencyManagement && insideDependencies;
    }

    private boolean isInsideDependenciesTag(final String line, final boolean insideDependenciesTag) {
        boolean insideDependencies = insideDependenciesTag;
        if (line.contains(DEPENDENCIES_TAG))
            insideDependencies = true;
        if (line.contains(DEPENDENCIES_END_TAG))
            insideDependencies = false;
        return insideDependencies;
    }

    private boolean isOutsideDependencyManagementTag(final String line, final boolean outsideDependencyManagementTag) {
        boolean outsideDependencyManagement = outsideDependencyManagementTag;

        if (line.contains("<dependencyManagement>"))
            outsideDependencyManagement = false;
        if (line.contains("</dependencyManagement>"))
            outsideDependencyManagement = true;
        return outsideDependencyManagement;
    }

}
