package com.goff.field.application;

import java.util.Iterator;

import main.java.com.goff.censobasicoweb.rest.relatorios.perfis.AbstractObject;
import main.java.com.goff.censobasicoweb.rest.relatorios.perfis.RelPerfisDocenteEscolaDTOResultEscolas;

import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import com.goff.field.application.otherService;

public class FieldUsebByPublicMethodInPrivateWithForBlock {

    private Service methodInFor;
    private Service methodInForBody;
    private Service methodInForEach;
    private Service methodInForEachBody;
    private Service forIterator;

    public void methodWithAllFieldsCalledByPrivateMethods() {
        forIteratorMethod();
        forMethod();
        forEachMethod();
    }

    private void forIteratorMethod() {
        for (final Iterator<Object> it = forIterator.registros().iterator(); it.hasNext();) {
            final VariableNotInitialized var;
        }
    }

    private void forMethod() {
        for (int i = methodInFor.intValue(); i < 10; i++) {
            methodInForBody.toString();
        }
    }

    private void forEachMethod() {
        for (String var : methodInForEach.strings) {
            methodInForEachBody.toString();
        }
    }

}
