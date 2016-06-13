package com.goff.parameter;

public class ObjectSample {

    public void invalidMethod(final ObjectSample os) {  // Noncompliant {{Objetos não devem receber eles mesmos como parâmetros.}}

    }
    public void invalidMethod2(String str, final ObjectSample sample) {  // Noncompliant {{Objetos não devem receber eles mesmos como parâmetros.}} 

    }
    
    public void validMethod(final String objectSample) { 

    }
}
