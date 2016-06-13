package com.goff.mapaweb.application.enem.aluno.uf;

public class FieldInArgument {

    private String fieldCalledInArgument;
    private String fieldCalledInNewClassArgument;
    private String fieldCalledInNewClassMethodArgument;

    public void validFieldUse() {
        method(fieldCalledInArgument);
        new SomeClass(fieldCalledInNewClassArgument)
        new SomeClass().calling(fieldCalledInNewClassMethodArgument);
    }
}
