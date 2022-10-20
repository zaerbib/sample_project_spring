package com.apress.todo.redis;

import com.apress.todo.domain.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ToDoProducer {
    private final RedisTemplate redisTemplate;

    public ToDoProducer(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void send(String topic, ToDo toDo){
        redisTemplate.convertAndSend(topic, toDo);
    }
}
