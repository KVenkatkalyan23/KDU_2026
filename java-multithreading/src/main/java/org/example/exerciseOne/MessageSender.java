package org.example.exerciseOne;

public class MessageSender implements Runnable {
     private MessageQueue messageQueue;

    public MessageSender(MessageQueue messageQueue){
        this.messageQueue = messageQueue;
    }

    @Override 
    public void run() {
        for(int i=0;i<5;i++){
            String str = "Message from the producer " + System.currentTimeMillis() + Thread.currentThread().getId();
            messageQueue.put(str);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
