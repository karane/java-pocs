package org.karane.annotations;

import javassist.*;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class ToStringProcessor {
    public static void main(String[] args) throws Exception {
        // Set up class pool for modification
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("target/classes"); // Ensure classes are loaded from target directory

        File classesDir = new File("target/classes/models");
        if (!classesDir.exists()) {
            System.err.println("No compiled classes found. Run `mvn compile` first.");
            return;
        }

        for (File classFile : Objects.requireNonNull(classesDir.listFiles())) {
            String className = "models." + classFile.getName().replace(".class", "");
            CtClass cc = pool.get(className);

            if (cc.hasAnnotation(ToString.class)) {
                System.out.println("Processing: " + className);
                generateToString(cc);
                cc.writeFile("target/classes");
            }
        }

        System.out.println("ToString method generation completed.");
    }

    private static void generateToString(CtClass cc) throws Exception {
        StringBuilder methodBody = new StringBuilder("public String toString() { return \"")
                .append(cc.getSimpleName()).append("{");

        for (CtField field : cc.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                methodBody.append(field.getName()).append("=\" + this.").append(field.getName()).append(" + \", ");
            }
        }

        if (cc.getDeclaredFields().length > 0) {
            methodBody.delete(methodBody.length() - 2, methodBody.length()); // Remove trailing comma
        }

        methodBody.append("}\"; }");

        CtMethod toStringMethod = CtNewMethod.make(methodBody.toString(), cc);
        cc.addMethod(toStringMethod);
    }
}
