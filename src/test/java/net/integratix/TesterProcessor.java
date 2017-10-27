package net.integratix;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TesterProcessor extends CamelTestSupport {

    private CamelContext context;
    @Produce(uri = "direct:in")
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:out")
    private MockEndpoint mockOut;

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        MyRouteBuilderWithProcessor routeBuilder = new MyRouteBuilderWithProcessor();
        routeBuilder.setSourceUri("direct:in");
        routeBuilder.setTargetUri("mock:out");
        return routeBuilder;
    }
    @Override
    protected CamelContext createCamelContext() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("headerBean",new HeaderBean());
        CamelContext camelContext = new DefaultCamelContext(registry);
        return camelContext;
    }

    @Test
    public void testMe() throws Exception {
        String barteltMsg="MSH|^~\\&|lab|UX|commserv|HIS1|20170728145439||ORU^R01|1501246479002|P|2.2|||AL|NE\r" +
            "PID||2017000001|52076400||TSU^Shasha||19521011|M|||Endstreet 14^^Belfast^^11040^A||||||||1123333331\r" +
            "PV1||I|K08I^4|||||||||||||N|||XYZ123\r" +
            "OBR||0042||^KIDNEY|||20170728140000|||||||20170728140000|||||||||||F\r" +
            "OBX||NM|TP^TP||4.4|g/dl|6.6 - 8.7|LL|||F|||20170728145100\r";

        mockOut.expectedMessageCount(1);
        mockOut.expectedBodiesReceived(barteltMsg.replaceAll("\r","\n"));
        mockOut.expectedHeaderReceived("caseno","XYZ123");
        mockOut.expectedHeaderReceived("caseno_outside_bean","XYZ123");
        mockOut.setResultWaitTime(1000);
        template.sendBody("direct:in",barteltMsg);
        mockOut.assertIsSatisfied();
    }

}
