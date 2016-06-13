package com.goff.field.application;

import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import com.goff.field.application.otherService;

public class FieldUsebByPublicMethodInPrivateWithWhileBlock {

    private Service methodInWhile;
    private Service methodInWhileBody;

    public void methodWithAllFieldsCalledByPrivateMethods() {
        thirdMethod();
    }

    private void thirdMethod() {
        while (methodInWhile.isTrue()) {
            methodInWhileBody.ok();
        }
    }

}
