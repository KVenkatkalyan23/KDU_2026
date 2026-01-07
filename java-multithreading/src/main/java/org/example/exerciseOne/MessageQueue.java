package org.example.exerciseOne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageQueue {
   private static List<String> messages  = new ArrayList<>();

   public synchronized void put(String var1) {
      messages.add(var1);
      this.notifyAll();
   }

   public String take() throws InterruptedException {
      if (messages.isEmpty()) {
         this.wait();
      }

      synchronized(this) {
         String var1 = messages.get(0);
         messages.remove(0);
         return var1;
      }
   }
}
