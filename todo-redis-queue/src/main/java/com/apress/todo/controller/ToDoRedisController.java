package com.apress.todo.controller;

import com.apress.todo.domain.ToDo;
import com.apress.todo.redis.ToDoProducer;
import com.apress.todo.repository.ToDoRepository;
import com.apress.todo.validation.ToDoValidationErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
public class ToDoRedisController {

    private final ToDoProducer toDoProducer;
    private final ToDoRepository toDoRepository;

    @Value("${apress.redis.rate}")
    private String topic;

    public ToDoRedisController(ToDoProducer toDoProducer, ToDoRepository toDoRepository) {
        this.toDoProducer = toDoProducer;
        this.toDoRepository = toDoRepository;
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        toDoProducer.send(topic, toDo);
        log.info("Producer > Message sent !");
        return ResponseEntity.ok().build();
    }
}
