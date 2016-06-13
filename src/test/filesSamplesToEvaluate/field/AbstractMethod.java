package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class AbstractMethod {

    abstract String abstractMethod();

    public void methodCallingAbstractOne() {
        String xxx = abstractMethod();
    }
}
