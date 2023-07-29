package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class RandomNumbersService {
    public double generateRandomNumber() {
        return Math.random();
    }

    public double generateRandomNumberInRange(int min, int max) {
        return Math.random() * (max - min);
    }
}
