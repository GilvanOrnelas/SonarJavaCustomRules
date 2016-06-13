package com.goff.rule.application.transactional;

@MyAnnotation 
@TransactionAttribute(REQUIRED)
public  class StatlessFailWithOnlyOthersAnnotations { // Noncompliant {{A anotação Stateless é obrigatória para classes da camada application.}}
    
    @Stateless
    public void teste(){
        
    }
}

