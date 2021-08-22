package com.teamnoctis.nox.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Slash {

    String name();

    String description();

}
