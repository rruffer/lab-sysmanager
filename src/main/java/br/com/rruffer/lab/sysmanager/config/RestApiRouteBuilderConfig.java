package br.com.rruffer.lab.sysmanager.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiRouteBuilderConfig extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration()
        .contextPath("/{{camel.springboot.name}}")
        .apiContextPath("/api-doc")
        	.apiProperty("host", "{{app.rest.api.hostname}}")
            .apiProperty("api.title", "{{app.rest.api.title}}")
            .apiProperty("api.version", "{{app.rest.api.version}}")
            .apiProperty("cors", "true")
            .apiProperty("schemes", "https")
        .component("servlet")
        .bindingMode(RestBindingMode.json)
        .skipBindingOnErrorCode(false)
    	.enableCORS(true)
		.corsHeaderProperty(
			"Access-Control-Allow-Headers", 
			"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
	}

}
