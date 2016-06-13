package com.goff.field.application;

import java.io.InputStream;

import main.java.com.goff.censobasicoweb.application.escola.Escola;
import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class TryCatchBlock {

    private final OtherService otherService;
    private String normalField;
    private InputStream streamField;

    public void methodWithAllFieldsInTryCatch() {
        try (InputStream stream = streamField;){
            otherService.doSomething();
        } catch (Exception e) {
            someClass.useField(normalField);
        }
    }

}
