package com.goff.rule.infra.method;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;

import com.goff.rule.infra.collection.CollectionCopier;

public class Method {

    private final String name;
    private final Set<String> members;
    private final Modifier methodVisibility;
    private final Set<String> innerMethods;

    public Method(final MethodTree member) {
        name = member.simpleName().toString();
        methodVisibility = Scope.find(member);
        members = Member.findAll(member);
        innerMethods = InnerMethod.findAllMethods(member);
    }

    public boolean isNotPrivate() {
        return methodVisibility != Modifier.PRIVATE;
    }

    public Set<String> fieldsNotUsedByMethod(final Set<String> fields, final Set<Method> methods) {
        if (members.containsAll(fields))
            return Collections.emptySet();

        final String fieldsNotUsedByThisMethod = fieldsNotUsed(methods, fields).toString();
        return methodsAndTheirMissingFields(fieldsNotUsedByThisMethod);
    }

    private Set<String> methodsAndTheirMissingFields(final String fieldsNotUsedByThisMethod) {
        final Set<String> methodsWithTheirMissingFields = new HashSet<>();
        final boolean someFieldIsNotUsedByThisMethod = !fieldsNotUsedByThisMethod.contains("[]");

        if (someFieldIsNotUsedByThisMethod)
            methodsWithTheirMissingFields
                    .add(String.format("Método: %s - Campos não utilizados: %s", this, fieldsNotUsedByThisMethod));
        return methodsWithTheirMissingFields;
    }

    private Set<String> fieldsNotUsed(final Set<Method> methods, final Set<String> fields) {
        final Set<String> fieldsNotUsed = CollectionCopier.copyWithoutTheReferences(fields);

        fieldsNotUsed.removeAll(members);
        for (final Method method : methods)
            removeFieldsPresentsInInnerMethods(fieldsNotUsed, method);
        return fieldsNotUsed;
    }

    private void removeFieldsPresentsInInnerMethods(final Set<String> fieldsNotUsed, final Method method) {
        for (final String inMethod : innerMethods)
            if (method.equals(inMethod))
                fieldsNotUsed.removeAll(method.members);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return name == null ? true : false;
        boolean isEqual = false;
        if (obj instanceof Method) {
            final Method method = (Method) obj;
            isEqual = name.equals(method.name);
        }
        return isEqual || name.equals(obj.toString());
    }

    @Override
    public String toString() {
        return name;
    }

}
