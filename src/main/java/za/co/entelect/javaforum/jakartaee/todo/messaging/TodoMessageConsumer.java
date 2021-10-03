package za.co.entelect.javaforum.jakartaee.todo.messaging;


import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.MessageDrivenContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.extern.java.Log;

import java.io.StringReader;

@Log
@MessageDriven(name ="todomdb" , activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "jms/todoQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue")
})
public class TodoMessageConsumer implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    private static final String ACTION_PROPERTY = "ChangeEvent";
    private static final String USER_PROPERTY = "User";

    @Override
    public void onMessage(Message message) {

        try {
            String action = message.getStringProperty(ACTION_PROPERTY);
            String user = message.getStringProperty(USER_PROPERTY);
            String body = message.getBody(String.class);

            JsonObject jsonObject = getJsonObject(body);
            log.info("==========Received Message==========");
            log.info(body.toString());
            log.info("==========End Message================");

        } catch (JMSException ex) {
            log.info("exception occurred "+ ex.getMessage());
            mdc.setRollbackOnly();
            throw new RuntimeException(ex);
        }
    }

    private JsonObject getJsonObject(String json){

        final JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }
}
