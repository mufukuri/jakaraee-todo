package za.co.entelect.javaforum.jakartaee.todo.json;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.json.*;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.stream.JsonGenerator;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class JsonMessage {

    private JsonWriterFactory writerFactory;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        this.writerFactory = Json.createWriterFactory(properties);
    }

    public String toJsonMessage(Todo todo){
        try(StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw)){

            JsonObject json = Json.createObjectBuilder()
                    .add("systemId", "todoapp")
                    .add("systemVersion", "1.0.0")
                    .add("timestamp", getTimeStamp())
                    .add("card", getTodoJsonObject(todo))
                    .build();

            jsonWriter.write(json);
            return sw.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private JsonObject getTodoJsonObject(Todo todo){
        String json = JsonbBuilder.create().toJson(todo);
        final JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    private String getTimeStamp(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
}
