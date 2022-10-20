package com.apress.todo.jms;

import com.apress.todo.domain.ToDo;
import com.apress.todo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class ToDoConsumer {
    private Logger log = LoggerFactory.getLogger(ToDoConsumer.class);
    private final ToDoRepository repository;

    public ToDoConsumer(ToDoRepository repository) {
        this.repository = repository;
    }

    @JmsListener(destination =  "simple-jms-queue")
    public void processToDo(@Valid ToDo todo){
        log.info("Consumer > "+todo);
        log.info("ToDo created > "+this.repository.save(todo));
    }

    @JmsListener(destination =  "${active.apress.topic}")
    public void processToDoFromTopic(@Valid ToDo todo){
        log.info("Consumer from topic> "+todo);
        log.info("ToDo created from topic > "+this.repository.save(todo));
    }
}
