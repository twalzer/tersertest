package net.integratix;

import org.apache.camel.Exchange;
import org.apache.camel.Headers;
import org.apache.camel.Processor;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.component.hl7.Terser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import static org.apache.camel.component.hl7.HL7.terser;

class HeaderProcessor implements Processor {
    private static Logger log = LoggerFactory.getLogger("HeaderProcessor");

    public void process(Exchange exchange) throws Exception {
        ValueBuilder result = terser("/PATIENT_RESULT/PATIENT/PV1-19-1");
        final String caseno = result.evaluate(exchange,String.class);
        log.info("caseno in processor: "+caseno);
        exchange.getIn().setHeader("caseno",caseno);
    }
}
