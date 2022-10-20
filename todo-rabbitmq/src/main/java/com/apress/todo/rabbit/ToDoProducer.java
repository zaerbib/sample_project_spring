package com.apress.todo.rabbit;

import com.apress.todo.domain.ToDo;
import com.apress.todo.validator.ToDoValidationErrorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class ToDoProducer {
    private static final Logger log = LoggerFactory.getLogger(ToDoProducer.class);
    private final RabbitTemplate template;

    @Value("${todo.amqp.queue}")
    private String destination;

    @Value("${todo.amqp.exchange}")
    private String exchange;

    public ToDoProducer(RabbitTemplate template) {
        this.template = template;
    }

    @RequestMapping(value="/todo", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> sendTo(@Valid @RequestBody ToDo toDo, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        this.template.convertAndSend(exchange,destination,toDo);
        log.info("Producer> Message Sent");
        return ResponseEntity.ok().build();
    }
}
