package org.karane.archunit.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // Available at runtime
@Target(ElementType.TYPE)          // Can be applied to classes
public @interface Entity {
    String description() default "Default description";
}