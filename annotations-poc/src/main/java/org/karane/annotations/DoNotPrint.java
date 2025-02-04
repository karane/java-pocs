package org.karane.annotations;

import java.lang.annotation.*;


//TODO: do not overwrite the toString of all objects!
@Retention(RetentionPolicy.RUNTIME) // Available at runtime
@Target(ElementType.FIELD)          // Can be applied to fields
public @interface DoNotPrint {
}
