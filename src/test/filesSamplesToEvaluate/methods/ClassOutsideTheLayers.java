package com.goff.methods;


public class ClassOutsideTheLayers {
    
    private void methodNotAllowed() { // Noncompliant {{Não deve haver comportamento (método) fora das camadas definidas (infrastructure;domain;application;rest).}}
        
    }
}