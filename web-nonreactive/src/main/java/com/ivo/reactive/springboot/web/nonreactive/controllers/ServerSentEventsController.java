package com.ivo.reactive.springboot.web.nonreactive.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/sse")
public class ServerSentEventsController {


    Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping()
    public SseEmitter subscribe() {

        // this can be a username or something that will identify the client
        String uuid = UUID.randomUUID().toString();

        // no timeout - 0l
        var emitter = new SseEmitter(0l);
        emitter.onCompletion(() -> {
            var removed = emitters.remove(uuid);
            log.info("Emitter with id={} has completed. Removed: {}", uuid, removed);
        });
        emitter.onError((e) -> {
            log.error("Emitter with id=" + uuid + " has thrown exception. Marking as complete.", e);
            emitter.complete();
        });
        emitter.onTimeout(() -> {
            log.info("Emitter with id={} has timed out. Marking as complete.", uuid);
            emitter.complete();
        });

        emitters.put(uuid, emitter);

        log.info("Emitter with id={} was registered.", uuid);

        return emitter;
    }

    @PostMapping("/publish")
    public void publishEvent(@RequestBody String message) {

        var eventId = UUID.randomUUID().toString();
        log.info("Broadcasting event with following message: {}", message);
        emitters.forEach((k, v) -> {
            try {
                v.send(SseEmitter.event()
                        .id(eventId)
                        .name("sampleEvent")
                        .data(message)
                        .reconnectTime(10000));

            } catch (IOException e) {
                log.info("Exception occurred while sending event to emitter: {}", k);
                v.completeWithError(e);
            }
        });
    }
}
