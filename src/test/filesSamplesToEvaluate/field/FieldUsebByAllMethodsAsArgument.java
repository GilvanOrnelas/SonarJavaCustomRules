package com.goff.field.application;

import com.goff.field.application.otherService;

public class FieldUsebByAllMethodsAsArgument {

    private String fieldArgumentToPrivate;
    private Service fieldObjectAsArgument;
    private Service fieldInArgumentInside;

    public void methodWithAllFields() {
        methodWithArgument(fieldArgumentToPrivate.doSome());
        someMethod().call(fieldObjectAsArgument.doSome().someOther());
        someMethod().call(fieldObjectAsArgument.doSome().someOther().oneMore());
        someMethod().call(fieldObjectAsArgument.doSome().someOther(fieldInArgumentInside).oneMore());
    }

    private void methodWithArgument(String arg) {
    }
}
