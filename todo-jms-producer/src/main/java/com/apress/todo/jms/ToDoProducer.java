package com.apress.todo.jms;

import com.apress.todo.domain.ToDo;
import com.apress.todo.validator.ToDoValidationErrorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ToDoProducer {
    private final Queue queue;
    private final JmsTemplate jmsTemplate;
    private static final Logger log = LoggerFactory.getLogger(ToDoProducer.class);

    @Value("${active.apress.topic}")
    private String destination;

    @Autowired
    public ToDoProducer(Queue queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @RequestMapping(value="/todo", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        jmsTemplate.convertAndSend(queue, toDo);
        log.info("Producer > Message Sent !");
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/todo/topic", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> createTodoFromeTopic(@Valid @RequestBody ToDo toDo, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        jmsTemplate.convertAndSend(destination, toDo);
        log.info("Producer from topic > Message Sent !");
        return ResponseEntity.ok().build();
    }
}
