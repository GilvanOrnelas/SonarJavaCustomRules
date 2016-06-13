package com.goff.rule.domain.single_responsibility;

import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.ModifierKeywordTree;
import org.sonar.plugins.java.api.tree.ModifierTree;
import org.sonar.plugins.java.api.tree.ModifiersTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.goff.rule.infra.method.Method;
import com.goff.rule.infra.variable.Variable;

public final class Member {

    private Member() {
        super();
    }

    static String fields(final Tree member) {
        final VariableTree variableTree = (VariableTree) member;
        return Variable.name(variableTree);
    }

    static Method methods(final Tree member) {
        return new Method((MethodTree) member);
    }

    static boolean methodIsNotAbstract(final Tree member) {
        final BlockTree bodyOfMethod = ((MethodTree) member).block();
        final boolean methodHasABody = bodyOfMethod != null;
        return methodHasABody;
    }

    static boolean isNotAConstant(final Tree member) {
        final ModifiersTree modifierTree = ((VariableTree) member).modifiers();
        final Set<Modifier> modifiers = modifiers(modifierTree);

        final boolean isConstant = modifiers.contains(Modifier.FINAL) && modifiers.contains(Modifier.STATIC);
        return !isConstant;
    }

    static boolean isAMethod(final Tree member) {
        return member.is(Kind.METHOD);
    }

    static boolean isAField(final Tree member) {
        return member.is(Kind.VARIABLE);
    }

    private static Set<Modifier> modifiers(final ModifiersTree modifierTrees) {
        final Set<Modifier> modifiers = new HashSet<>();
        for (final ModifierTree modifierTree : modifierTrees)
            modifiers.add(modifierKeyword(modifierTree));
        return modifiers;
    }

    private static Modifier modifierKeyword(final ModifierTree modifierTree) {
        if (modifierTree instanceof ModifierKeywordTree)
            return ((ModifierKeywordTree) modifierTree).modifier();

        return Modifier.DEFAULT;
    }

}
