package com.leverx.entity;

import com.leverx.type.AgeClassification;

public class Dog {
    private static final int MAX_PUPPY_AGE = 1;
    private static final int MIN_OLD_AGE = 8;
    private int age;
    private boolean healthy;
    private boolean hungry;
    private boolean trained;
    private AgeClassification type;

    public Dog(int age, boolean healthy, boolean hungry, boolean trained) {
        this.age = age;
        this.healthy = healthy;
        this.hungry = hungry;
        this.trained = trained;
        updateType();
    }

    private void updateType() {
        if (age <= MAX_PUPPY_AGE) {
            type = AgeClassification.PUPPY;
        } else if (age >= MIN_OLD_AGE) {
            type = AgeClassification.OLD;
        } else {
            type = AgeClassification.ADULT;
        }
    }

    public int getAge() {
        return age;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public boolean isHungry() {
        return hungry;
    }

    public boolean isTrained() {
        return trained;
    }

    public AgeClassification getType() {
        return type;
    }

    void eat(){
        hungry = false;
    }

    void beHungry(){
        hungry = true;
    }

    void recover(){
        healthy = true;
    }

    void train(){
        trained = true;
    }

    void work(){
        beHungry();
    }
}
