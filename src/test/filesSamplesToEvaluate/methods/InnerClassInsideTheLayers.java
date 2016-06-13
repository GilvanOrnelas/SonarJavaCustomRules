package com.goff.infrastructure.log;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class InnerClassInsideTheLayers {

    private static final String JNDI_BEAN_MANAGER = "java:comp/BeanManager";

    private static final class InnerClass {
        private void innerClassMethod() {

        }
    }
}
