package org.karane.model;

import org.karane.annotations.ToString;
import org.karane.annotations.DoNotPrint;
import org.karane.annotations.Printable;

import java.lang.reflect.Field;

@Printable(description = "This is a user class")
@ToString
public class User {
    private String name;
    private int age;
    @DoNotPrint
    private String address;

    public User(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName() + " { ");
        Field[] fields = getClass().getDeclaredFields();

        for (Field field : fields) {
            if (! field.isAnnotationPresent(DoNotPrint.class)) {
                field.setAccessible(true); // Access private fields
                try {
                    result.append(field.getName())
                            .append(" = ")
                            .append(field.get(this))
                            .append(", ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // Remove the trailing comma and space, if present
        if (result.length() > 2) {
            result.setLength(result.length() - 2);
        }

        result.append(" }");
        return result.toString();
    }
}
