package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class ObjectCallInsideBlockWhile {

    private final OtherService fieldObject;
    private String normalField;
    private String normalFieldAssign;
    private List<String> fieldList;

    public void whileBlock() {
        while (fieldList) {
            normalField = fieldObject.stringValue();
            normalFieldAssign = "";
        }
        while (true) {
        }
    }
    
}
