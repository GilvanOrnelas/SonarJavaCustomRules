package com.goff.mapaweb.application.enem.aluno.uf;

public class ReturningMethodExpression {

    private Field validField;

    public void obterNumeroMeta(final Long codigoUf) throws DomainException, InfrastructureException {
        return validField.obterNumeroMeta(codigoUf);
    }

}
