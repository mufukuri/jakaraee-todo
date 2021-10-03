package za.co.entelect.javaforum.jakartaee.todo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;

import java.time.LocalDate;

@Log
@ApplicationScoped
public class Startup {


    @PersistenceContext(name= "todoapp")
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        log.info("========Generating some Todos =====");
        Todo firstTodo = generateMockTodo();
        em.persist(firstTodo);
    }

    private Todo generateMockTodo() {

        Todo todo = new Todo();
        todo.setDateCreated(LocalDate.now());
        todo.setDueDate(LocalDate.of(2022,01,01));
        todo.setTask("Get a drivers license.");
        return todo;
    }
}
