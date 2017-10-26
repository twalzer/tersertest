package net.integratix;

import org.apache.camel.Expression;
import org.apache.camel.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.apache.camel.component.hl7.HL7.terser;

public class HeaderBean {
    private static Logger log = LoggerFactory.getLogger("HeaderBean");
    public void setHeaders(@Headers Map headers) throws Exception {
        Expression caseno=terser("/PATIENT_RESULT/PATIENT/PV1-19-1");
        log.info("caseno:"+caseno.toString());
        headers.put("caseno",caseno);
    }
}
