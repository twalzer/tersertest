package net.integratix;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        from("file:src/data?noop=true")
            .bean("headerBean","setHeaders")
            .log("header is ${header.caseno}")
            .to("mock:out")
        ;
    }

}
