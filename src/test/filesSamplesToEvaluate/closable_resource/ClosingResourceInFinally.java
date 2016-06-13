package com.goff.annotation;

import java.io.InputStream;

public class ElseInIfWithReturn {

    private InputStream stream;

    public String invalidResourceClose() {

        try { // Noncompliant {{Recursos do tipo Closable devem ser fechados com o try-with-resources.}}
            stream = SomeClass.stream();
        } catch (Exception e) {

        } finally {
            stream.close();
        }

    }

}
