package org.acme.quarkus.sample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

/**
 * HelloService
 */
@ApplicationScoped
public class HelloService {

    public String hello() {
        return "Hello!";
    }

    public String sayHello(String name) {
        return String.format("Hello %s!", name);
    }

    public CompletionStage<String> sayAsyncHello(String name) {
        return CompletableFuture.supplyAsync(() -> sayHello(name));
    }

    @Incoming("greetings")
    @Outgoing("translated-greetings-stream")
    public String linguaGreeting(String greeting) {
        return greeting;
    }
}
