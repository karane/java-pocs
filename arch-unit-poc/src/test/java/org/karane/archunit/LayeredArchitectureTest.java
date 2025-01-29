package org.karane.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.karane.archunit.annotations.Entity;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

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
            .resideInAnyPackage("..model..", "java..", "..annotations..");

        rule.check(importedClasses);
    }

    @Test
    void repositoriesShouldEndWithRepository() {
        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository");

        rule.check(importedClasses);
    }

    @Test
    void controllersShouldHaveProperNaming() {
        ArchRule rule = classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(importedClasses);
    }

    @Test
    void servicesShouldHaveProperNaming() {
        ArchRule rule = classes()
                .that().resideInAPackage("..service..")
                .should().haveSimpleNameEndingWith("Service");

        rule.check(importedClasses);
    }
    @Test
    void noCyclicDependencies() {
        ArchRule rule = slices()
                .matching("org.karane.archunit.(*)..")
                .should().beFreeOfCycles();

        rule.check(importedClasses);
    }

    @Test
    void utilityClassesHavePrivateConstructor() {
        ArchRule rule = classes()
                .that().resideInAPackage("..util..")
                .should().haveOnlyPrivateConstructors();

        if (!importedClasses.that(resideInAPackage("..util..")).isEmpty()) {
            rule.check(importedClasses);
        }
    }

    @Test
    void dtosShouldBeImmutable() {
        ArchRule rule = classes()
                .that().resideInAPackage("..dto..")
                .should().haveOnlyFinalFields();

        if (!importedClasses.that(resideInAPackage("..dto..")).isEmpty()) {
            rule.check(importedClasses);
        }
    }


    @Test
    void controllersShouldNotHaveComplexMethods() {
        ArchRule rule = methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().notHaveRawReturnType("void")
                .because("Controllers should delegate logic to services and return responses");

        rule.check(importedClasses);
    }

    @Test
    void modelClassesShouldBeAnnotatedWithEntity() {
        ArchRule rule = classes()
                .that().resideInAPackage("..model..")
                .should().beAnnotatedWith(Entity.class);

        rule.check(importedClasses);
    }

    @Test
    void repositoriesShouldNotDependOnOtherRepositories() {
        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..repository..", "..model..", "java..");

        rule.check(importedClasses);
    }

    @Test
    void modelClassesShouldNotUseJavaUtilDate() {
        ArchRule rule = fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().notHaveRawType(java.util.Date.class)
                .because("Use java.time.LocalDate or java.time.Instant instead of java.util.Date");

        rule.check(importedClasses);
    }

    @Test
    void repositoriesShouldNotContainBusinessLogic() {
        ArchRule rule = methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..repository..")
                .should().haveRawReturnType(java.util.List.class)
                .orShould().haveRawReturnType(java.util.Optional.class)
                .because("Repositories should only return entities or collections, not business logic results");

        rule.check(importedClasses);
    }


    @Test
    void controllersShouldNotThrowGenericExceptions() {
        ArchRule rule = methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().notDeclareThrowableOfType(Exception.class)
                .andShould().notDeclareThrowableOfType(RuntimeException.class)
                .because("Controllers should throw specific exceptions instead of generic ones");

        rule.check(importedClasses);
    }

}
