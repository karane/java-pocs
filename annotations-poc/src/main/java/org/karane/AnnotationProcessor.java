package org.karane;

import org.karane.annotations.Printable;
import org.karane.model.Order;
import org.karane.model.Product;
import org.karane.model.User;

import java.util.ArrayList;
import java.util.List;

public class AnnotationProcessor {
    public static void printAnnotatedObjects(List<Object> objects) {
        for (Object obj : objects) {
            Class<?> clazz = obj.getClass();
            if (clazz.isAnnotationPresent(Printable.class)) {
                Printable annotation = clazz.getAnnotation(Printable.class);
                System.out.println("Class: " + clazz.getSimpleName());
                System.out.println("Description: " + annotation.description());
                System.out.println("Object: " + obj.toString());
                System.out.println("--------------------");
            }
        }
    }

    public static void main(String[] args) {
        // Create some objects
        List<Object> objects = new ArrayList<>();
        objects.add(new User("Alice", 25, "Av. Baroes 900, Porto Alegre, Brazil"));
        objects.add(new Product("Laptop", 999.99));
        objects.add(new Order(123, "Shipped"));

        // Print annotated objects
        printAnnotatedObjects(objects);
    }
}
