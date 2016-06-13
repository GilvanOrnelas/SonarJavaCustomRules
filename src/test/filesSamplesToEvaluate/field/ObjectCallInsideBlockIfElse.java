package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class ObjectCallInsideBlockIfElse {

    private final OtherService fieldObject;
    private String normalField;
    private String normalFieldInElse;
    private final OtherService fieldObjectInElse;
    private String normalFieldInIfElse;
    private final OtherService fieldObjectInIfElse;
    
    private String normalFieldInInnerIf;
    private final OtherService fieldObjectInInnerIf;

    public void ifElseBlock() {
        if (true) {
            normalField = fieldObject.fieldCall();
        } else if ("".equals("")) {
            normalFieldInIfElse = fieldObjectInIfElse.fieldElseCall();
            if (true) { 
                normalFieldInInnerIf = fieldObjectInInnerIf.fieldElseCall();
            }
        } else {
            normalFieldInElse = fieldObjectInElse.fieldElseCall();
        }
    }
}
