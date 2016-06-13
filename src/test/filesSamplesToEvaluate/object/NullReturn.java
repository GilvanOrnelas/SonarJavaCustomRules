package com.goff.object;


public class NullReturn {
    
    public String invalidMethod() { 
        return null; // Noncompliant {{Null não deve ser retornado.}}
    }
    
    public String validMethod() { 
        return ""; 
    }
}