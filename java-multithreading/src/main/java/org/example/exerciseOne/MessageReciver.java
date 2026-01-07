package org.example.exerciseOne;

public class MessageReciver implements Runnable {
    private MessageQueue messageQueue;

    public MessageReciver(MessageQueue messageQueue){
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
