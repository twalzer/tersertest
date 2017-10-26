package net.integratix;

import org.apache.camel.builder.RouteBuilder;

import static org.apache.camel.component.hl7.HL7.terser;


/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    public void setSourceUri(String sourceUri) {
        this.sourceUri = sourceUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    private String sourceUri;
    private String targetUri;
    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        sourceUri="direct://in";
        targetUri="mock://out";
        from(sourceUri)
            .unmarshal().hl7()
            .setHeader("sending_fac",terser("MSH-4"))
            .log("msh4: "+ header("sending_fac"))
            .bean("headerBean","setHeaders")
            .log("header is ${header.caseno}")
            .transform(regexReplaceAll(body(), "\\r","\\n"))
            .log("message is ${body}")
            .to(targetUri)
        ;
    }

}
