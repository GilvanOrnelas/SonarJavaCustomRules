package com.goff.annotation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Class
public class FieldAnnotation {

    @Field // Noncompliant {{A anotação deve ser colocados no método/construtor.}}
    private InvalidField invalid;

    @Contructor
    public FieldAnnotation() {

    }

    @Method
    public void validAnnotationPlace(final ValidField field) {

    }
    
    public void paramAnnotation(@Parameter ValidField field) {

    }

}
