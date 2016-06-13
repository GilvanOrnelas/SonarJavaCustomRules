package com.goff.rule.infra.dynamic.classreader;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import com.goff.rule.infra.tree.ClassUnreadableException;

final class ClassReader extends SimpleFileVisitor<Path> {

    final Set<Class<?>> classes = new HashSet<>();
    final Set<Path> paths = new HashSet<>();
    private final String pathStart;

    ClassReader(final String pathStart) {
        this.pathStart = pathStart;
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        if (Files.isRegularFile(file) && file.toString().endsWith(".java"))
            addClasses(file);
        return FileVisitResult.CONTINUE;
    }

    private void addClasses(final Path file) {
        try {
            paths.add(file);
            classes.add(
                    Class.forName(file.toString().replace(pathStart, "").replace(".java", "").replaceAll("/", ".")));
        }
        catch (final ClassNotFoundException e) {
            throw new ClassUnreadableException("Não foi possível ler o arquivo " + file, e);
        }
    }
}
