package com.goff.rule.domain.packages;

import java.util.HashSet;
import java.util.Set;

public class Package {

    private final String packageName;
    private int amountOfEntities;
    private boolean reported;

    public Package(final String packageName) {
        this.packageName = packageName;
    }

    public Package(final String packageName, final int amountOfEntities) {
        this.packageName = packageName;
        this.amountOfEntities = amountOfEntities;
    }

    void increaseAmountOfEntities() {
        amountOfEntities += 1;
    }

    boolean hasMoreEntitiesThanThe(final int allowedAmountOfEntities) {
        return amountOfEntities > allowedAmountOfEntities;
    }

    static Set<Package> notYetReported(final Set<Package> packagesWithIssue) {
        final Set<Package> notYetReporteds = new HashSet<>();
        for (final Package pkg : packagesWithIssue) {
            if (!pkg.reported) {
                pkg.reported = true;
                notYetReporteds.add(pkg);
            }
        }
        return notYetReporteds;
    }

    @Override
    public String toString() {
        return packageName;
    }

    @Override
    public int hashCode() {
        return packageName.length();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof String) {
            return packageName.equals(obj);
        }

        final Package otherPackage = (Package) obj;
        return packageName.equals(otherPackage.packageName);
    }

}
