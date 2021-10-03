package za.co.entelect.javaforum.jakartaee.todo.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;
import za.co.entelect.javaforum.jakartaee.todo.service.TodoService;

import java.util.List;

@Log
@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    @Inject
    TodoService todoService;


    @POST
    public Response createTodo(Todo todo) {
        log.info("Creating a todo");
        todoService.createTodo(todo);
        return Response.ok(todo).build();
    }


    @PUT
    public Response updateTodo(Todo todo) {

        todoService.update(todo);
        return Response.ok(todo).build();
    }

    @Path("{id}")
    @GET
    public Todo getTodo(@PathParam("id") Long id) {
        return todoService.findToDoById(id);
    }


    @GET
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }
}
