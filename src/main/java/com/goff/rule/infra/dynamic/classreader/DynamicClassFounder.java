package com.goff.rule.infra.dynamic.classreader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import com.goff.rule.infra.tree.ClassUnreadableException;

public final class DynamicClassFounder {

    private DynamicClassFounder() {
        super();
    }

    public static Set<Class<?>> readMainClassesFrom(final Package pkg) {
        return readClassesFrom("src/main/java/", pkg);
    }

    public static Set<Class<?>> readClassesFrom(final String pathStart, final Package pkg) {
        final Path path = Paths.get(pathStart + pkg.getName().replaceAll("\\.", "/"));

        final ClassReader classVisitor = new ClassReader(pathStart);
        readClassFiles(path, classVisitor);

        return classVisitor.classes;
    }

    private static void readClassFiles(final Path path, final ClassReader classVisitor) {
        try {
            Files.walkFileTree(path, classVisitor);
        }
        catch (final IOException e) {
            throw new ClassUnreadableException("Não foi possível ler o caminho " + path, e);
        }
    }
}
