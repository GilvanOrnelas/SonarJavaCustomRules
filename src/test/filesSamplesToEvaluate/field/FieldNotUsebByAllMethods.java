package com.goff.field.application;

import com.goff.field.application.otherService;

public class FieldUsebByAllMethods { // Noncompliant {{Os seguintes métodos não utilizam os respectivos campos: [Método: methodUsingFields - Campos não utilizados: [normalFieldNotUsed]]}}

    private final OtherService otherService;
    private String normalField;
    private String normalFieldNotUsed;

    public void methodUsingFields() { 
        otherService.doSomething();
        someClass.useField(normalField);
    }
}
