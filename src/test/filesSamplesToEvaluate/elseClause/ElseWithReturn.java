package com.goff.annotation;

public class ElseInIfWithReturn {

    public String invalidElse() {
        if (true) {// Noncompliant {{Cláusula "else" é desnecessária neste bloco.}}
            return true;
        } else {
            return false;
        }

    }

    public String invalidElse(final String str) {
        if (str == "") {// Noncompliant {{Cláusula "else" é desnecessária neste bloco.}}
            invalidElse();
            return "";
        } else if (str.equals("2")) {
            final String result = new String("2");
            return result;
        } else {
            final String a = "";
        }
        return "";
    }
}
