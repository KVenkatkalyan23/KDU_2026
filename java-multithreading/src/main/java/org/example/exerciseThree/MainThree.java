package org.example.exerciseThree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MainThree {

    public static void callableExample() throws InterruptedException, ExecutionException{
          Callable<Integer> callableTask = () -> {
            int sum = 0;
            for(int i=0;i<10;i++){
                sum += i;
            }
            Thread.sleep(3000);
            return sum;
        };

           ExecutorService executorService = Executors.newFixedThreadPool(3);
           Future<Integer> val =  executorService.submit(callableTask);
           System.out.println("Waiting...");
           Integer value = val.get();
           System.out.println("After get()");
           System.out.println(value);
           executorService.shutdown();
    }

    public static void streamExample(){

        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<1000000;i++){
            list.add(random.nextInt(10));
        }

        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();

        long sum = list
        .stream()
        .mapToInt(Integer::intValue)
        .sum();

        endTime = System.currentTimeMillis();

        System.out.println("Time taken by normal stream :" + (endTime -startTime));
        System.out.println("sum by normal stream :" + sum);

        startTime = System.currentTimeMillis();

        sum = list
        .parallelStream()
        .mapToInt(Integer::intValue)
        .sum();

        endTime = System.currentTimeMillis();

        System.out.println("Time taken by parallel stream :" + (endTime -startTime));
        System.out.println("sum by parallel stream :" + sum);
        
    }
    
}
