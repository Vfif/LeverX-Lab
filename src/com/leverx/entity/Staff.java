package com.leverx.entity;

import java.util.List;

public class Staff {

    public void feed(List<Dog> dogs) {
        dogs.forEach(Dog::eat);
    }

    public void cleanAviaries(List<Aviary> aviaries) {
        aviaries.forEach(Aviary::clean);
    }

    public void train(TrainingArea trainingArea, List<Dog> puppies) {
        puppies.forEach(Dog::train);
    }

    public void setDogsInAviaries(List<Dog> oldDogs, List<Aviary> aviaries) {
        oldDogs.forEach(Dog::beHungry);
    }

    public void accompanyToWork(List<Dog> adultDogs) {
        adultDogs.forEach(Dog::work);
    }
}
