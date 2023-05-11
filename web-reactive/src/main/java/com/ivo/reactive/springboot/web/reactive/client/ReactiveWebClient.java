package com.ivo.reactive.springboot.web.reactive.client;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ReactiveWebClient {

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        WebClient client1 = WebClient.create("http://localhost:8080");
        var flux1 = client1.get()
                .uri("/reactive/items")
                .retrieve()
                .bodyToFlux(Object.class);


        // call non-reactive service
        WebClient client2 = WebClient.create("http://localhost:8181");
        var flux2 = client2.get()
                .uri("/nonreactive/items")
                .retrieve()
                .bodyToFlux(Object.class);

        // merge the two flux publishers and subscribe
        Flux.merge(flux1, flux2).subscribe(System.out::println);

    }
}
