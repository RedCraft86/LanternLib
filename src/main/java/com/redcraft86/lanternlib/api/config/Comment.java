package com.redcraft86.lanternlib.api.config;

import java.lang.annotation.*;

/**
 * Required to insert property comments into the JSON file.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Comments.class)
public @interface Comment {
    String value() default "";
}
