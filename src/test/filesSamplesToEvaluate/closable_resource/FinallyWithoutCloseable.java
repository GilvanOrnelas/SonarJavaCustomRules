package com.goff.annotation;

import java.io.InputStream;

public class FinallyWithoutCloseable {

    private Service service;

    public String validCall() {
        try {
            stream = SomeClass.stream();
        } catch (Exception e) {

        } finally {
            service.close();
        }

    }

}
