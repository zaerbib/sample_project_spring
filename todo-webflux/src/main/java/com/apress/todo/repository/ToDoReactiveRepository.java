package com.apress.todo.repository;

import com.apress.todo.domain.ToDo;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public class ToDoReactiveRepository implements CommonReactiveRepository<ToDo>{
    private final ReactiveMongoTemplate mongoTemplate;

    public ToDoReactiveRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<ToDo> save(ToDo domain) {
        return mongoTemplate.save(domain);
    }

    @Override
    public Flux<ToDo> save(Collection<ToDo> domains) {
        return Flux.fromIterable(domains).flatMap(mongoTemplate::save);
    }

    @Override
    public Mono<Long> delete(ToDo domain) {
        Query query = new Query(Criteria.where("id").is(domain.getId()));
        return mongoTemplate.remove(query, ToDo.class).map(DeleteResult::getDeletedCount);
    }

    @Override
    public Flux<Long> deleteAll() {
        return mongoTemplate.findAllAndRemove(new Query(), "toDo");
    }

    @Override
    public Mono<ToDo> findById(String id) {
        return mongoTemplate.findById(id, ToDo.class);
    }

    @Override
    public Flux<ToDo> findAll() {
        return mongoTemplate.findAll(ToDo.class);
    }
}
