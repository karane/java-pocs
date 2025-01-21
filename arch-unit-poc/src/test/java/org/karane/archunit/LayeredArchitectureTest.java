package org.karane.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class LayeredArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("org.karane.archunit");

    @Test
    void controllersShouldOnlyDependOnServices() {
        ArchRule rule = classes()
            .that().resideInAPackage("..controller..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..controller..", "..service..", "java..");

        rule.check(importedClasses);
    }

    @Test
    void servicesShouldOnlyDependOnRepositoriesAndModels() {
        ArchRule rule = classes()
            .that().resideInAPackage("..service..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..service..", "..repository..", "..model..", "java..");

        rule.check(importedClasses);
    }

    @Test
    void repositoriesShouldNotDependOnControllersOrServices() {
        ArchRule rule = classes()
            .that().resideInAPackage("..repository..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..repository..", "..model..", "java..");

        rule.check(importedClasses);
    }

    @Test
    void modelsShouldBeIndependent() {
        ArchRule rule = classes()
            .that().resideInAPackage("..model..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..model..", "java..");

        rule.check(importedClasses);
    }

    @Test
    void repositoriesShouldEndWithRepository() {
        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository");

        rule.check(importedClasses);
    }
}
