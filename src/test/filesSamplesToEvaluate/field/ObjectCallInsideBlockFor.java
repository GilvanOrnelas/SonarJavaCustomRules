package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class ObjectCallInsideBlockFor {

    private List<String> fieldListOfStrings;
    private OtherObject objectWithListOfStrings;
    private final OtherService fieldObject;
    private String normalField;

    public void forBlock() {
        for(String ob : fieldListOfStrings){
            normalField = fieldObject.fieldCall();
        }
        for(String ob : objectWithListOfStrings.listOfStrings()){
        }
    }
}
