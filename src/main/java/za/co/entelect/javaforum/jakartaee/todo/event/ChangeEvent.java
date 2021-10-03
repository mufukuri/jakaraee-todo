package za.co.entelect.javaforum.jakartaee.todo.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEvent {
    @NotNull
    private Todo todo;
    @NotNull
    private ChangeEventType type;
}
