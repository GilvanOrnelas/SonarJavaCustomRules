package com.goff.rule.rest.transactional;

@MyAnnotation 
@Statless
public  class RequestScopedFailWithOnlyOthersAnnotations { // Noncompliant {{A anotação RequestScoped é obrigatória para classes da camada REST.}}
    
    @Statless
    public void teste(){
        
    }
}

