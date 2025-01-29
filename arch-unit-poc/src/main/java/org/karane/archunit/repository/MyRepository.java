package org.karane.archunit.repository;

import org.karane.archunit.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class MyRepository {
    public List<UserEntity> getData() {

        System.out.println("Fetching data...");

        return new ArrayList<>();
    }
}
