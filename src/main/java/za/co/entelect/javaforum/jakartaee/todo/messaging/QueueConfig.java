package za.co.entelect.javaforum.jakartaee.todo.messaging;

import jakarta.annotation.Resource;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.Queue;
import lombok.extern.java.Log;

@Log
public class QueueConfig {

    @Produces
    public Queue exposeQueue() {
        return this.queue;
    }

   @Resource(lookup = "jms/todoQueue")
   private Queue queue;

}
