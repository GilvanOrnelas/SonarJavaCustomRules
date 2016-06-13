package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class UsebByAllMethodAsObjectCallToSet {

    private final OtherService fieldObject;
    private FieldToSet setter;

    public void methodWithAllFields() {
        setter.defineValue(fieldObject.fieldCall());
    }
    
}
