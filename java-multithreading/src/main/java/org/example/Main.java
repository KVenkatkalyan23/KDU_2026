package org.example;

import org.example.exerciseOne.MainOne;
import org.example.exerciseThree.MainThree;
import org.example.exerciseTwo.MainTwo;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MainOne.run();
        MainTwo.run();
        MainThree.callableExample();
        MainThree.streamExample();
    }
}