package org.karane.archunit.controller;

import org.karane.archunit.service.MyService;

public class MyController {
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    public void handleRequest() {
        myService.performService();
    }
}
