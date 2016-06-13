package com.goff.rule.infra.collection;

import java.util.HashSet;
import java.util.Set;

public final class CollectionCopier {

    private CollectionCopier() {
        super();
    }

    public static <T> Set<T> copyWithoutTheReferences(final Set<T> collection) {
        final Set<T> newSet = new HashSet<>();
        for (final T element : collection)
            newSet.add(element);
        return newSet;
    }
}
