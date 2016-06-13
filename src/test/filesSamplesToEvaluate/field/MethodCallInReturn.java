package com.goff.mapaweb.application.enem.aluno.uf;

public class ReturningField {

    private SomeField fieldToReturn;

    public String obterNumeroMeta(final Long codigoUf) {
        return fieldToReturn.toString();
    }

    public void emptyReturn(final Long codigoUf) {
        fieldToReturn = "";
        if (true) {
            return;
        }
    }
}
