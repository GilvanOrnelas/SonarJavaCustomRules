package com.goff.annotation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Class
public class ValidFieldAnnotation {

    @Field
    private ValidInvalidField valid;


}
