package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class UsebByAllMethodAsObjectCallToAssign {

    private final OtherService fieldObject;
    private String normalField;

    public void methodWithAllFields() {
        normalField = fieldObject.fieldCall();
    }
    

}
