package org.acme.quarkus.sample;

import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import io.smallrye.mutiny.Multi;

@Path("/hello")
public class HelloResource {

    @Inject
    HelloService helloService;

    @Channel("translated-greetings-stream")
    Multi<String> linguaGreetings;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return helloService.hello();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@PathParam("name") String name) {
        return helloService.sayHello(name);
    }

    @GET
    @Path("/async/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> sayAsyncHello(@PathParam("name") String name) {
        return helloService.sayAsyncHello(name);
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> linguaGreeting(){
        return linguaGreetings;
    }

}
