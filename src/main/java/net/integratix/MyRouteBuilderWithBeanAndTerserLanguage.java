package net.integratix;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;

import static org.apache.camel.component.hl7.HL7.terser;


/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilderWithBeanAndTerserLanguage extends RouteBuilder {

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
        HL7DataFormat hl7 = new HL7DataFormat();

        from(sourceUri)
            .unmarshal(hl7)
            .setHeader("sending_fac",terser("MSH-4"))
            .log("msh4: ${header.sending_fac}")
            .setHeader("caseno_outside_bean",terser("/PATIENT_RESULT/PATIENT/PV1-19-1"))
            .log("caseno outside bean: ${header.caseno_outside_bean}")
            .bean("headerBean","setHeaders")
            .log("caseno from inside bean/header: ${header.caseno}")
            .transform(regexReplaceAll(body(), "\r","\n"))
            .log("message is ${body}")
            .to(targetUri)
        ;
    }

}
