package com.ivo.reactive.springboot.web.reactive.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("reactive")
public class ReactiveController {

    @GetMapping("/items")
    public Flux<Object> getItems() {
        return Flux.fromStream((IntStream.range(0, 5).mapToObj(i -> "reactive-" + UUID.randomUUID().toString())));
    }
}
