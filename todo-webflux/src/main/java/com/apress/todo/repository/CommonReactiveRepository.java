package com.apress.todo.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CommonReactiveRepository<T> {
    public Mono<T> save(T domain);
    public Flux<T> save(Collection<T> domains);
    public Mono<Long> delete(T domain);
    public Flux<Long> deleteAll();
    public Mono<T> findById(String id);
    public Flux<T> findAll();
}
