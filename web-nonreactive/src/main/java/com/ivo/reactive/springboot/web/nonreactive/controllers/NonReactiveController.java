package com.ivo.reactive.springboot.web.nonreactive.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("nonreactive")
@Slf4j
public class NonReactiveController {

    @GetMapping("/items")
    public ResponseEntity<List<String>> getSampleItems() {

        var uuid = UUID.randomUUID().toString();
        log.info("Handling request " + uuid );

        List items = new ArrayList<String>();
        for (var i = 0; i < 5; i++) {
            items.add("nonreactive-" + UUID.randomUUID().toString());
        }

        log.info("Request completed " + uuid );
        return ResponseEntity.ok(items);
    }
}
