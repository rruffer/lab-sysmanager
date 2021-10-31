package br.com.rruffer.lab.sysmanager.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.stereotype.Service;

import br.com.rruffer.lab.sysmanager.domain.Cliente;

@Service
public class ListenerFileRoute extends RouteBuilder {
	
	public static final String ID = "{{route.listener.file.id}}";
	public static final String URI = "{{route.listener.file.uri}}";

	@Override
	public void configure() throws Exception {
		
		from(URI)
			.routeId(ID)
			.convertBodyTo(String.class)
			.log("li o arquivo...")
			.log("${body}")
			.unmarshal(new ListJacksonDataFormat(Cliente.class))
			.log("Buscar cidade e estado")
			.to(CorreiosApiRoute.URI)
		.end();
		
	}

}
