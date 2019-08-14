package com.test.annotations;

public @interface WarmUp {
    //预热，默认次数为0
    int iterations() default 0;
}
