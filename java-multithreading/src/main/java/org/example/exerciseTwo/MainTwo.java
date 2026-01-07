package org.example.exerciseTwo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTwo {
    public static void run() throws InterruptedException, ExecutionException{
        MessageQueue2 messageQueue = new MessageQueue2();
        ExecutorService senderexecutorService = Executors.newFixedThreadPool(3);
        ExecutorService readerexecutorService = Executors.newFixedThreadPool(3);

        for(int i=0;i<3;i++){
            senderexecutorService.submit(new MessageSender2(messageQueue));
            readerexecutorService.submit(new MessageReciver2(messageQueue));
        }

        senderexecutorService.shutdown();
        readerexecutorService.shutdown();
        
    }
}