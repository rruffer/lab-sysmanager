package br.com.rruffer.lab.sysmanager.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class ListenerFileRoute extends RouteBuilder {
	
	public static final String ID = "{{route.listener.file.id}}";
	public static final String URI = "{{route.listener.file.uri}}";

	@Override
	public void configure() throws Exception {
		
		from(URI)
			.routeId(ID)
			.log("li o arquivo...")
			.log("${body}")
		.end();
		
	}

}
