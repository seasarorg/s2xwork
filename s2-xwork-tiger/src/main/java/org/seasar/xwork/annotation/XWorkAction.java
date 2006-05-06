package org.seasar.xwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface XWorkAction {
    String name();

    String method() default "execute";

    String converter() default "";

    Result[] result() default {};

    Param[] param() default {};

    String[] interceptorRef() default {};

    String[] externalRef() default {};
}