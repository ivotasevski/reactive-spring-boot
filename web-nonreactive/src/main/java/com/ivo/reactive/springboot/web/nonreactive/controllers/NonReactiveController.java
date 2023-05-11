package com.ivo.reactive.springboot.web.nonreactive.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("nonreactive")
public class NonReactiveController {

    @GetMapping("/items")
    public ResponseEntity<List<String>> getSampleItems() {
        List items = new ArrayList<String>();
        for (var i = 0; i < 5; i++) {
            items.add("nonreactive-" + UUID.randomUUID().toString());
        }
        return ResponseEntity.ok(items);
    }
}
