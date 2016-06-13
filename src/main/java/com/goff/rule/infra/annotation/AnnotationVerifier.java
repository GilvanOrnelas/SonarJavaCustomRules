package com.goff.rule.infra.annotation;

import java.util.List;

import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeTree;

public final class AnnotationVerifier {

    private AnnotationVerifier() {
        super();
    }

    public static boolean annotationExists(final String annotationName, final ClassTree tree) {
        final List<AnnotationTree> annotations = tree.modifiers().annotations();
        boolean exists = false;

        for (final AnnotationTree annotationTree : annotations)
            exists = exists || expectedAnnotationExists(annotationName, annotationTree);

        return exists;
    }

    private static boolean expectedAnnotationExists(final String annotationName, final AnnotationTree annotationTree) {
        final TypeTree annotationType = annotationTree.annotationType();
        return annotationTree.annotationType().is(Tree.Kind.IDENTIFIER)
                && annotationName.equalsIgnoreCase(((IdentifierTree) annotationType).name());
    }
}
