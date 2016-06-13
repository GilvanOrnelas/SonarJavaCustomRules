package com.goff.rule.infra.sonar;

import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;

public class SonarBaseTreeRule extends BaseTreeVisitor implements JavaFileScanner {

    protected JavaFileScannerContext context;

    @Override
    public void scanFile(final JavaFileScannerContext ctx) {
        context = ctx;
        scan(ctx.getTree());
    }

    protected void addGenericIssue(final String errorMessage) {
        final int defaultLine = 1;
        context.addIssue(defaultLine, this, errorMessage);
    }

    protected void addIssue(final Tree tree, final String errorMessage) {
        context.addIssue(tree, this, errorMessage);
    }
}
