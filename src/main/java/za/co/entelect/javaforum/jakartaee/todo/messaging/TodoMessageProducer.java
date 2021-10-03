package za.co.entelect.javaforum.jakartaee.todo.messaging;

import jakarta.ejb.Stateless;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import lombok.extern.java.Log;
import za.co.entelect.javaforum.jakartaee.todo.event.ChangeEvent;
import za.co.entelect.javaforum.jakartaee.todo.json.JsonMessage;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;

@Log
@Stateless
public class TodoMessageProducer {

    @Inject
    private JMSContext context;

    @Inject
    private Queue queue;

    @Inject
    private JsonMessage jsonMessage;

    private static final String ACTION_PROPERTY = "ChangeEvent";
    private static final String USER_PROPERTY = "User";

    public void receiveChangeEvent(@ObservesAsync ChangeEvent event) throws JMSException {

        log.info("Change event "+ event);

        Todo todo = event.getTodo();
        String json = jsonMessage.toJsonMessage(todo);

        JMSProducer producer = context.createProducer();
        producer.setProperty(ACTION_PROPERTY, event.getType().name());
        producer.setProperty(USER_PROPERTY, "TestAccount");
        log.info("======Sending to queue " + queue.getQueueName());
        producer.send(queue, json);
    }
}
