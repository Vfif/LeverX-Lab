package com.leverx.entity;

import java.util.List;

public class Vet {
    public void check(List<Dog> dogs) {
        dogs.stream()
                .filter(this::isNotHealthy)
                .forEach(Dog::recover);
    }

    private boolean isNotHealthy(Dog dog) {
        return !dog.isHealthy();
    }
}
