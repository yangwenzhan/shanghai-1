package com.tianqiauto.textile.weaving.util.log;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface Logger {
    String msg() default "";
}
