package net.integratix;

import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class Tester extends TestCase {

    private CamelContext context;
    private ProducerTemplate template;

    public void setUp() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("headerBean",new HeaderBean());
        context=new DefaultCamelContext(registry);
        template=context.createProducerTemplate();
        context.addRoutes(new RouteBuilder() {
            public void configure() throws Exception {
                from("direct:in")
                        .bean("headerBean","setHeaders")
                        .log("header is ${header.caseno}")
                        .to("mock:out")
                ;
            }
        });

        context.start();
    }

    @Test
    public void testMe() throws Exception {

        String barteltMsg="MSH|^~\\&|lab|UX|commserv|HIS1|20170728145439||ORU^R01|1501246479002|P|2.2^^|||AL|NE|||\r" +
            "PID||2017000001|52076400||TSU^Shasha||19521011|M|||Endstreet 14^^Belfast^^11040^A||||||||1123333331||||||||||||\r" +
            "PV1||I|K08I^4^|||||^^||||||||N|||2017012345||||||||||||||||||||||||||||||||||\r" +
            "OBR||0042||^NIERE|||20170728140000|||||||20170728140000|||||||||||F||||||^|^^^^^|||\r" +
            "OBX||NM|TP^TP||4.4|g/dl|6.6 - 8.7|LL|||F|||20170728145100|\r";

        template.requestBody("direct:in",barteltMsg);
    }

}
