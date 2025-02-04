# Annotation Processor Example
This project demonstrates how to use custom annotations in Java to process objects dynamically at runtime. Specifically, it filters and prints objects based on a @Printable annotation applied at the class level.

## Features
- Define and use custom annotations.
- Dynamically process objects at runtime using Java Reflection.
- Filter and print only objects whose classes are annotated with a specific annotation.

## Prerequisites
- Java 8 or higher
- Maven for dependency management and build.

## How to Build and Run
1. Clone the Repository:

```bash
git clone https://github.com/yourusername/annotation-processor-example.git
cd annotation-processor-example
```

2. Build the Project: Use Maven to compile the project and package it:

```bash
mvn clean package
```

3. Run the Application: After building the project, run the main class:

```bash
java -cp target/annotation-processor-example-1.0-SNAPSHOT.jar AnnotationProcessor
```

## Expected Output
When you run the application, the program processes objects dynamically and prints details of those whose classes are annotated with @Printable. Example output:

```vbnet
Class: User
Description: This is a user class
Object: User { name = Alice, age = 25 }
--------------------
Class: Product
Description: This is a product class
Object: Product { name = Laptop, price = 999.99 }
--------------------
```

## Key Concepts
- Annotations: Metadata that provides additional information to the compiler or runtime.
- Reflection: Java's ability to inspect and manipulate classes, methods, and fields at runtime.
- Dynamic Filtering: Ensures only objects of classes annotated with @Printable are processed.
