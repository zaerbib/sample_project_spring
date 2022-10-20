package com.apress.todo.router;

import com.apress.todo.reactive.ToDoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Component
public class ToDoRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(ToDoHandler handler){
        return RouterFunctions.route(GET("/api/todo").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/api/todo/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(PATCH("/api/todo/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::setCompleted)
                .andRoute(POST("/api/todo").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(PUT("/api/todo").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(DELETE("/api/todo/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete)
                .andRoute(DELETE("/api/todo").and(accept(MediaType.APPLICATION_JSON)), handler::deleteAll);
    }
}
