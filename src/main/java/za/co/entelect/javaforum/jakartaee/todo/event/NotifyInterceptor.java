package za.co.entelect.javaforum.jakartaee.todo.event;

import jakarta.annotation.Priority;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.java.Log;
import za.co.entelect.javaforum.jakartaee.todo.model.Todo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.logging.Level;

@Notify
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Log
public class NotifyInterceptor implements Serializable {

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {
        Todo input = getTodoInput(ic.getParameters());
        Object returnObject = ic.proceed();
        Todo output = getTodoOutput(returnObject);
        notify(ic,input, output);
        return returnObject;
    }

    private void notify(InvocationContext ic,Todo input,Todo output){
        // TODO: Later change to include both ? For comparison ? For now find one

        Method method = ic.getMethod();
        String name = method.getName();
        Notify annotation = method.getAnnotation(Notify.class);


        if(output==null && input ==null){
            // Can not place the annotation on that method...
            log.log(Level.WARNING, "Ignoring invalid @Notify annotation on method [{0}] - "
                    + "method must at least return a Todo or have a Todo as a input parameter", name);
        }
        Todo todo = output;
        if(todo==null)
            todo = input;

        ChangeEvent changeEvent = new ChangeEvent(todo, annotation.value());
        broadcaster.fireAsync(changeEvent);
    }

    private Todo getTodoInput(Object[] parameters){
        for(Object param:parameters){
            if(param.getClass().equals(Todo.class)){
                return (Todo)param;
            }
        }
        return null;
    }

    private Todo getTodoOutput(Object response){
        if(response==null)return null;
        if(response.getClass().equals(Todo.class)){
            return (Todo)response;
        }
        return null;
    }

    @Inject
    private Event<ChangeEvent> broadcaster;
}
