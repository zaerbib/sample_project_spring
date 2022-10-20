package com.apress.todo.reactive;

import com.apress.todo.domain.ToDo;
import com.apress.todo.repository.ToDoReactiveRepository;
import com.mongodb.internal.connection.Server;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ToDoHandler {
    private final ToDoReactiveRepository toDoReactiveRepository;

    public ToDoHandler(ToDoReactiveRepository commonReactiveRepository) {
        this.toDoReactiveRepository = commonReactiveRepository;
    }

    public Mono<ServerResponse> save(ServerRequest request){
        return request.bodyToMono(ToDo.class)
                .flatMap(todo -> this.toDoReactiveRepository.save(todo))
                .flatMap(p -> ServerResponse.created(URI.create("/todo/" + p.getId())).build());
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        return request.bodyToMono(ToDo.class)
                .flatMap(todo -> toDoReactiveRepository.delete(todo))
                .flatMap(p -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        return toDoReactiveRepository.findById(request.pathVariable("id"))
                .flatMap(todo -> ServerResponse.ok().body(Mono.just(todo), ToDo.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().body(toDoReactiveRepository.findAll(), ToDo.class);
    }

    public Mono<ServerResponse> setCompleted(ServerRequest request){
        return toDoReactiveRepository.findById(request.pathVariable("id"))
                .flatMap(todo -> {
                    todo.setCompleted(true);
                    return Mono.just(todo);
                })
                .flatMap(todo -> ServerResponse.ok().body(todo, ToDo.class))
                .flatMap(p -> ServerResponse.created(URI.create("/todo")).build());
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request){
        return ServerResponse.ok().body(toDoReactiveRepository.deleteAll(), ToDo.class);
    }
}
