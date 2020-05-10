package com.leverx.entity;

import java.util.List;

public class Vet {
    public void check(List<Dog> dogs) {
        dogs.forEach(dog -> {
            if (!dog.isHealthy()) {
                dog.recover();
            }
        });
    }
}
