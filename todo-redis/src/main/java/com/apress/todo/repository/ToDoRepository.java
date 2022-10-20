package com.apress.todo.repository;

import com.apress.todo.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ToDoRepository implements CommonRepository<ToDo>{
    public final RedisTemplate redisTemplate;
    private static final String HASH_KEY_NAME = "MENU-ITEM";

    private final Logger log = LoggerFactory.getLogger(ToDoRepository.class);

    public ToDoRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ToDo save(ToDo domain) {
        redisTemplate.opsForHash().put(HASH_KEY_NAME, domain.getId(), domain);
        log.info("todo create > "+domain);
        return domain;
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        for(ToDo domain: domains) {
            this.save(domain);
        }
        return domains;
    }

    @Override
    public void delete(ToDo domain) {
        redisTemplate.opsForHash().delete(HASH_KEY_NAME, domain.getId());
    }

    @Override
    public ToDo findById(String id) {
        return (ToDo) redisTemplate.opsForHash().get(HASH_KEY_NAME, id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return redisTemplate.opsForHash().values(HASH_KEY_NAME);
    }
}
