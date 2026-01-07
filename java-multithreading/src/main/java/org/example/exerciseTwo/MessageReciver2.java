package org.example.exerciseTwo;

public class MessageReciver2 implements Runnable{

    private MessageQueue2 messageQueue;

    public MessageReciver2(MessageQueue2 messageQueue){
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        for(int i=0;i<5;i++){
            try {
                String message = messageQueue.take();
                System.out.println(
                    Thread.currentThread().getName() + " received: " + message
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}

