package za.co.entelect.javaforum.jakartaee.todo.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import za.co.entelect.javaforum.jakartaee.todo.event.ChangeEventType;
import za.co.entelect.javaforum.jakartaee.todo.event.Notify;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;

import java.util.List;

@Log
@RequestScoped
public class TodoService {

    @PersistenceContext(name = "todoapp")
    EntityManager entityManager;

    @Transactional
    @Notify(ChangeEventType.create)
    public Todo createTodo(@NotNull Todo todo) {
        log.info("=====Generating todo for " +todo.getTask());
        entityManager.persist(todo);
        return todo;
    }

    @Transactional
    public Todo update(@NotNull Todo todo) {
        entityManager.merge(todo);
        return todo;
    }


    public Todo findToDoById(@NotNull @Min(value = 0L) Long id) {
        return entityManager.find(Todo.class, id);
    }

    @Transactional
    public void removeToDoById(@NotNull @Min(value = 0L) Long id) {
        Todo todo = findToDoById(id);
        entityManager.remove(todo);
    }

    public List<Todo> getTodos() {
        return entityManager.createQuery("SELECT t from Todo t", Todo.class).getResultList();
    }

}
