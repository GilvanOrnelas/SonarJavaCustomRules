package com.goff.rule.infra.starter;

import org.sonar.plugins.java.api.JavaCheck;

import com.goff.rule.domain.PomPropertiesChecks;
import com.goff.rule.domain.RequestScopedRequiredCheck;
import com.goff.rule.domain.StatelessRequiredCheck;
import com.goff.rule.domain.TransactionAttributeRequiredCheck;
import com.goff.rule.domain.annotation.AnnotationPlaceCheck;
import com.goff.rule.domain.beans.BeansXmlCheck;
import com.goff.rule.domain.beans.BeansXmlTestCheck;
import com.goff.rule.domain.clause_else.ElseClauseCheck;
import com.goff.rule.domain.closable_resource.ClosableResourceCheck;
import com.goff.rule.domain.dependency.DependencyScopeCheck;
import com.goff.rule.domain.dependency.DependencyVersionCheck;
import com.goff.rule.domain.dependency.OutsideDependencyCheck;
import com.goff.rule.domain.method.MethodOutsideLayersCheck;
import com.goff.rule.domain.object.NullAssignmentCheck;
import com.goff.rule.domain.object.NullReturnCheck;
import com.goff.rule.domain.packages.BelowPackageAccessCheck;
import com.goff.rule.domain.packages.EntityPerPackageCheck;
import com.goff.rule.domain.parameter.ObjectReceiveItSelfCheck;
import com.goff.rule.domain.single_responsibility.SingleResponsibilityPrincipleCheck;
import com.goff.rule.domain.test_properties.TestPropertiesCheck;

final class RulesAvailables {

    @SuppressWarnings("unchecked")
    static final Class<? extends JavaCheck>[] RULES = new Class[] { TransactionAttributeRequiredCheck.class,
            StatelessRequiredCheck.class, RequestScopedRequiredCheck.class, PomPropertiesChecks.class,
            DependencyScopeCheck.class, OutsideDependencyCheck.class, TestPropertiesCheck.class, BeansXmlCheck.class,
            BeansXmlTestCheck.class, DependencyVersionCheck.class, EntityPerPackageCheck.class,
            MethodOutsideLayersCheck.class, ObjectReceiveItSelfCheck.class, AnnotationPlaceCheck.class,
            ElseClauseCheck.class, NullAssignmentCheck.class, SingleResponsibilityPrincipleCheck.class, NullReturnCheck.class,
            BelowPackageAccessCheck.class, ClosableResourceCheck.class };

    @SuppressWarnings("unchecked")
    static final Class<? extends JavaCheck>[] TEST_RULES = new Class[] {};

    private RulesAvailables() {
        super();
    }

}
