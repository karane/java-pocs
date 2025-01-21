package org.karane.archunit.service;

import org.karane.archunit.repository.MyRepository;

public class MyService {
    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public void performService() {
        System.out.println("Service logic here...");
        myRepository.getData();
    }
}
