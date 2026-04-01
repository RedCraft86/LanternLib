package com.redcraft86.lanternlib.api.config.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Required for a property to be recognized as a config property. Also used to define nesting in a config.
 * <p>
 * Examples:
 * <ul>
 *   <li>{@code ""} (empty) will put the property into the root, without nesting it.</li>
 *   <li>{@code "SomeCategory"} will nest the property to SomeCategory > THIS_PROPERTY.</li>
 *   <li>{@code "Other.Category"} will nest the property to Other > Category > THIS_PROPERTY.</li>
 * </ul>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    String value() default "";
}
