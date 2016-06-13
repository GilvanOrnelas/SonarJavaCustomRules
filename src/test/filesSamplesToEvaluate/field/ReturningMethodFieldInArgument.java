package com.goff.mapaweb.application.enem.aluno.uf;

public class ReturningMethodExpression {

    private String fieldInArgument;

    public void validMethod() {
        return someMethod.obterNumeroMeta(fieldInArgument);
    }

}
