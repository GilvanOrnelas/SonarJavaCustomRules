package com.goff.rule.infra.packages;

import org.sonar.plugins.java.api.tree.ClassTree;

public final class PackageVerifier {

    private PackageVerifier() {
        super();
    }

    public static boolean isInPackage(final String packageToAnalyse, final ClassTree tree) {
        final String packageName = packageName(tree);
        return packageName.contains("." + packageToAnalyse);
    }

    public static boolean isInPackages(final ClassTree tree, final String... packageToAnalyse) {
        for (final String pkg : packageToAnalyse)
            if (isInPackage(pkg, tree))
                return true;
        return false;
    }

    public static String packageName(final ClassTree tree) {
        final String packageName = tree.symbol().owner().name();
        return packageName;
    }
}
