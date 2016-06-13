package com.goff.object;

public class NullAssignment {

    private String invalidFieldVariable = null; // Noncompliant {{Null não devem ser atribuído a variáveis.}}
    private String validFieldVariable = "";

    public void validMethod() {
        String invalidVariable = null; // Noncompliant {{Null não devem ser atribuído a variáveis.}}
        String validVariable = "";
    }
}
