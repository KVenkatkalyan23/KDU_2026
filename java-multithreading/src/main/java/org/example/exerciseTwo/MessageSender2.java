package org.example.exerciseTwo;

public class MessageSender2 implements Runnable{

    private MessageQueue2 messageQueue2;

    public MessageSender2(MessageQueue2 messageQueue){
        this.messageQueue2 = messageQueue;
    }

    @Override 
    public void run() {
        for(int i=0;i<5;i++){
            String str = "Message from the producer " + System.currentTimeMillis() + Thread.currentThread().getId();
            messageQueue2.put(str);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
