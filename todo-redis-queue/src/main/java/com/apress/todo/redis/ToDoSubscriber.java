package com.apress.todo.redis;

import com.apress.todo.domain.ToDo;
import com.apress.todo.repository.ToDoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ToDoSubscriber {

    private final ToDoRepository toDoRepository;

    public ToDoSubscriber(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public void handleMessage(ToDo toDo) {
        toDoRepository.save(toDo);
        log.info("ToDo created > "+toDo);
    }
}
