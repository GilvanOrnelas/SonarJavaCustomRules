package com.goff.field.application;

import main.java.com.goff.censobasicoweb.application.escola.Escola;
import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class FieldUsebByAllMethods {

    private final OtherService otherService;
    private String normalField;
    private String normalField2;

    public void methodWithAllFields() {
        otherService.doSomething();
        someClass.useField(normalField);
        String usingField = normalField2;
    }
    
}
