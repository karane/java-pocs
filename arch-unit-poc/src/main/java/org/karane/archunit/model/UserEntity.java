package org.karane.archunit.model;

import org.karane.archunit.annotations.Entity;

@Entity
public class UserEntity {
    private String name;

    public UserEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
