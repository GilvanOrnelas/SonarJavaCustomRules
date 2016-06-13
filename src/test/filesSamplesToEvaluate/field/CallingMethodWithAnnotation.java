package com.goff.field.application;

import com.goff.field.application.otherService;
import com.goff.mapaweb.domain.atividade.Atividade;

public class CallingMethodWithAnnotation {

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Atividade obterPorCodigo(final Long codigoAtividade) {

        return new Atividade().obterPorCodigo(codigoAtividade);
    }
}
