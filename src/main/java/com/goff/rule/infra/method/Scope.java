package com.goff.rule.infra.method;

import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.ModifierKeywordTree;
import org.sonar.plugins.java.api.tree.ModifierTree;
import org.sonar.plugins.java.api.tree.ModifiersTree;

final class Scope {

    private static final Modifier[] VISIBILITIES = { Modifier.PRIVATE, Modifier.DEFAULT, Modifier.PROTECTED,
            Modifier.PUBLIC };

    private Scope() {
        super();
    }

    static Modifier find(final MethodTree member) {
        final ModifiersTree modifiers = member.modifiers();
        Modifier visibility = Modifier.DEFAULT;
        for (final ModifierTree modifier : modifiers)
            if (modifier instanceof ModifierKeywordTree)
                visibility = findMethodVisibility(modifier);
        return visibility;
    }

    private static Modifier findMethodVisibility(final ModifierTree modifier) {
        final Modifier possibleVisibility = ((ModifierKeywordTree) modifier).modifier();
        if (isVisibilityModifier(possibleVisibility))
            return possibleVisibility;
        return Modifier.DEFAULT;
    }

    private static boolean isVisibilityModifier(final Modifier visibility) {
        for (final Modifier availableVisibility : VISIBILITIES)
            if (visibility.equals(availableVisibility))
                return true;
        return false;
    }
}
