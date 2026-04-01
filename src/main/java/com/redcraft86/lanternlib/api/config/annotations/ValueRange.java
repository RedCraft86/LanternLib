package com.redcraft86.lanternlib.api.config.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Determines the value min and max range of a numerical type.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueRange {
    double min() default 0.0;
    double max() default 1.0;
}
