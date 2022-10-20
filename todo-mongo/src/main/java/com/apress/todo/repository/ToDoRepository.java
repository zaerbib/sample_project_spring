package com.apress.todo.repository;

import com.apress.todo.domain.ToDo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ToDoRepository implements CommonRepository<ToDo>{
    public final MongoTemplate mongoTemplate;

    public ToDoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ToDo save(ToDo domain) {
        return mongoTemplate.save(domain);
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        return mongoTemplate.insertAll(domains);
    }

    @Override
    public void delete(ToDo domain) {
        mongoTemplate.remove(domain);
    }

    @Override
    public ToDo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, ToDo.class);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return mongoTemplate.findAll(ToDo.class);
    }
}
