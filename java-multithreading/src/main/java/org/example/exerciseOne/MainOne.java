package org.example.exerciseOne;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainOne {
    public static void run(){
        MessageQueue messageQueue = new MessageQueue();
        ExecutorService senderexecutorService = Executors.newFixedThreadPool(3);
        ExecutorService readerexecutorService = Executors.newFixedThreadPool(3);

        for(int i=0;i<3;i++){
            senderexecutorService.submit(new MessageSender(messageQueue));
            readerexecutorService.submit(new MessageReciver(messageQueue));
        }

        senderexecutorService.shutdown();
        readerexecutorService.shutdown();
    }
}
