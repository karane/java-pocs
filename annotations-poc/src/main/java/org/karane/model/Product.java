package org.karane.model;

import org.karane.annotations.DoNotPrint;
import org.karane.annotations.Printable;

import java.lang.reflect.Field;

@Printable(description = "This is a product class")
public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
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