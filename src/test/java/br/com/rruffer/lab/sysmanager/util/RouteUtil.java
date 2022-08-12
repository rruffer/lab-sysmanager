package br.com.rruffer.lab.sysmanager.util;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.InterceptSendToEndpointDefinition;
import org.springframework.stereotype.Component;

@Component
public class RouteUtil {
	
	public RouteBuilder mockRoute(MockEndpoint mockRoute, String URI) {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint(URI)
				.skipSendToOriginalEndpoint()
			//		.log("-----> Intercept URI: " + URI + "method: ${header.CamelHttpMethod}" )
				.log("-----> Disguising (${header.CamelHttpMethod}) " + URI + " as " + mockRoute.getEndpointUri() + "...")
				.to(mockRoute.getEndpointUri())
				.log("-----> BODY ON MOCK T IS ${body}");
			}
		};
	}

	public InterceptSendToEndpointDefinition mockRoute(AdviceWithRouteBuilder route, MockEndpoint mockRoute, String URI) {
		return route.interceptSendToEndpoint(URI)
				.log("-----> Disguising (${header.CamelHttpMethod}) " + URI + " as " + mockRoute.getEndpointUri() + "...")
				.skipSendToOriginalEndpoint()
				.to(mockRoute.getEndpointUri())
				.log("-----> BODY ON MOCK T IS ${body}");
	}

}
