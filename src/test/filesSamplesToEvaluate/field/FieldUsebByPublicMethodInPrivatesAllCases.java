package com.goff.field.application;

import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import com.goff.field.application.otherService;

public class FieldUsebByPublicMethodInPrivatesAllCases {

    private final OtherService otherService;
    private String normalField;
    private String normalField2;
    private Service methodBeingAssigned;
    private Service methodAsArgument;
    private Service methodInFortyMethodAsArgument;
    private Service methodInFortyMethodAsArgumentCallingOther;
    private Service methodInFortyMethod;
    private Service methodInWhile;
    private Service methodInWhileBody;
    private Service methodInForBody;
    private Service methodInFor;

    public void methodWithAllFieldsCalledByPrivateMethods() {
        completingTheFieldsMethod();
        secondCompletingMethod();
        String usingField = normalField2;
    }

    private void completingTheFieldsMethod() {
        otherService.doSomething();
    }

    private void secondCompletingMethod() {
        someClass.useField(normalField);
        thirdMethod();
    }

    private void thirdMethod() {
        String value = methodBeingAssigned.stringValue();
        while (methodInWhile.isTrue()) {
            if (true) {
                fortyMethod(methodAsArgument.toString());
                for (int i = methodInFor; i < 10; i++) {
                    String str = methodInForBody.toString();
                }
            }
            methodInWhileBody.ok();
        }
    }

    private void fortyMethod(String string) {
        methodInFortyMethod.set(methodInFortyMethodAsArgument);
        methodInFortyMethod.set(methodInFortyMethodAsArgumentCallingOther.toString());
    }

}
