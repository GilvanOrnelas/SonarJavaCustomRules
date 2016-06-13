package com.goff.rule.application.transactional;

@MyAnnotation 
@TransactionAttribute(REQUIRED)
public  class TransactionAttributeRequiredFailOtherAnnotations { // Noncompliant {{A anotação TransactionAttribute(NOT_SUPPORTED) é obrigatória para classes da camada application.}}
    
    @TransactionAttribute(NOT_SUPPORTED)
    public void teste(){
        
    }
}

