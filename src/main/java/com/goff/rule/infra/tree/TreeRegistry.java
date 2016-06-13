package com.goff.rule.infra.tree;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.sonar.plugins.java.api.tree.Tree;

import com.goff.rule.infra.dynamic.classreader.DynamicClassFounder;
import com.goff.rule.infra.tree.statement.AssignmentStrategy;
import com.goff.rule.infra.tree.statement.EmptyStrategy;

public final class TreeRegistry {

    private static final Map<Class<?>, Class<?>> TREE_STATEMENT = loadStrategyClasses();

    private TreeRegistry() {
        super();
    }

    private static <T> Map<Class<?>, Class<?>> loadStrategyClasses() {
        final Map<Class<?>, Class<?>> statements = new ConcurrentHashMap<>();
        final Set<Class<?>> strategyClasses = DynamicClassFounder
                .readMainClassesFrom(AssignmentStrategy.class.getPackage());

        for (final Class<?> strategyClass : strategyClasses)
            statements.put(getStatementFrom(strategyClass), strategyClass);

        return statements;
    }

    private static Class<?> getStatementFrom(final Class<?> strategyClass) {
        final Class<?> superClassParameterType = Tree.class;
        for (final Method method : strategyClass.getDeclaredMethods()) {
            final int firstParameter = 0;
            final Class<?> type = method.getParameterTypes()[firstParameter];
            if (isTheExpected(method) && isNotTheTreeClass(type))
                return type;
        }
        return superClassParameterType;

    }

    private static boolean isNotTheTreeClass(final Class<?> parameterType) {
        return !Tree.class.getSimpleName().equals(parameterType.getSimpleName());
    }

    private static boolean isTheExpected(final Method m) {
        return "stringValueFromAllMembers".equals(m.getName());
    }

    static <T extends TreeStatement<?>> T create(final Tree tree) {
        final Class<?> interfaceFromTree = interfaceFrom(tree.getClass());

        if (treeIsNotMapped(interfaceFromTree))
            return emptyStrategy();

        return createNewStatementInstance(interfaceFromTree);

    }

    private static <T extends TreeStatement<?>> T createNewStatementInstance(final Class<?> interfaceFromTree) {
        @SuppressWarnings("unchecked")
        final Class<T> statementClass = (Class<T>) TREE_STATEMENT.get(interfaceFromTree);

        final T newStatementInstance = createInstance(statementClass);
        return newStatementInstance;
    }

    private static <T extends TreeStatement<?>> T emptyStrategy() {
        @SuppressWarnings("unchecked")
        final T emptyStrategy = (T) new EmptyStrategy();
        return emptyStrategy;
    }

    private static boolean treeIsNotMapped(final Class<?> interfaceFromTree) {
        final boolean statementIsNotMapped = !TREE_STATEMENT.containsKey(interfaceFromTree);
        return statementIsNotMapped;
    }

    private static <T extends TreeStatement<?>> T createInstance(final Class<T> statementClass) {
        try {
            return statementClass.newInstance();
        }
        catch (final InstantiationException | IllegalAccessException e) {
            throw new TreeNotMappedException(String.format("Não foi possível instanciar a class %s", statementClass),
                    e);
        }
    }

    private static Class<?> interfaceFrom(final Class<?> clazz) {
        return clazz.getInterfaces()[0];
    }
}
