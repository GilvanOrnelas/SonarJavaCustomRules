package com.goff.field.application;

import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import com.goff.field.application.otherService;

public class FieldUsebByMethodsIndirectlyRecursive {

    private final Recursive method1;
    private final Recursive method2;
    private final Recursive method3;
    private final Recursive method4;

    public void methodWithAllFieldsCalledByPrivateMethods() {
        secondMethod();
        method1.toString();
    }

    private void secondMethod() {
        thirdMethod();
        method2.toString();
    }

    private void secondCompletingMethod() {
        someClass.useField(normalField);
        thirdMethod();
    }

    private void thirdMethod() {
        fortyMethod();
        method3.toString();
    }

    private void fortyMethod() {
        method4.toString();
    }

}
