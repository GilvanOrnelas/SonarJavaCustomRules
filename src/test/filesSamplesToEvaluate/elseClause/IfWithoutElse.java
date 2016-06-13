package com.goff.annotation;

public class IfWithoutElse {

    public String validBlock(final String str) {
        if (str == "") {
            return "";
        } 
        return "";
    }
    
    public String validBlock2(final String str) {
        if (str == "") {
            validBlock(str);
            return "";
        } 
        return "";
    }
}
