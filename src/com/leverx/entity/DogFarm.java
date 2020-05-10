package com.leverx.entity;

import com.leverx.type.AgeClassification;
import com.leverx.type.SizeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DogFarm {
    private List<Dog> dogs;
    private List<Aviary> aviaries;
    private TrainingArea trainingArea;
    private Staff staff;
    private Vet vet;

    public void dogsInit(Random random) {
        int dogCount = random.nextInt(50);
        IntStream.range(0, dogCount)
                .forEach(i -> {
                    int age = random.nextInt(10);
                    boolean healthy = random.nextBoolean();
                    boolean hungry = true;
                    boolean trained = random.nextBoolean();
                    dogs.add(new Dog(age, healthy, hungry, trained));
                });
    }

    public void aviariesInit(Random random) {
        int aviaryCount = dogs.size() + random.nextInt(10);
        IntStream.range(0, aviaryCount)
                .forEach(i -> {
                    SizeType type = SizeType.values()[random.nextInt(3)];
                    boolean clean = false;
                    aviaries.add(new Aviary(type, clean));
                });
    }

    public void init() {
        Random random = new Random();
        dogsInit(random);
        aviariesInit(random);
    }

    public DogFarm() {
        dogs = new ArrayList<>();
        aviaries = new ArrayList<>();
        trainingArea = new TrainingArea();
        staff = new Staff();
        vet = new Vet();
        init();
    }

    public void oneDay() {
        staff.feed(dogs);

        vet.check(dogs);

        staff.cleanAviaries(aviaries);

        List<Dog> puppies = dogs.stream()
                .filter(dog -> dog.getType().equals(AgeClassification.PUPPY))
                .collect(Collectors.toList());
        staff.train(trainingArea, puppies);

        List<Dog> adultDogs = dogs.stream()
                .filter(dog -> dog.getType().equals(AgeClassification.ADULT))
                .collect(Collectors.toList());
        staff.accompanyToWork(adultDogs);

        List<Dog> oldDogs = dogs.stream()
                .filter(dog -> dog.getType().equals(AgeClassification.OLD))
                .collect(Collectors.toList());
        staff.setDogsInAviaries(oldDogs, aviaries);

        staff.feed(dogs);
    }
}
