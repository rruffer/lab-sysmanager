package br.com.rruffer.lab.sysmanager.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.stereotype.Component;

import br.com.rruffer.lab.sysmanager.domain.Cliente;
import br.com.rruffer.lab.sysmanager.util.AppConstantes;

@Component
public class ListenerFileRoute extends RouteBuilder {
	
	public static final String ID = "{{route.listener.file.id}}";
	public static final String URI = "{{route.listener.file.uri}}";

	@Override
	public void configure() throws Exception {
		
		from(URI)
			.routeId(ID)
			.convertBodyTo(String.class)
			.log("Consumindo arquivo ${header.CamelFileNameConsumed}...")
			.log("Corpo do arquivo: ${body}...")
			.unmarshal(new ListJacksonDataFormat(Cliente.class))
			.setProperty(AppConstantes.CLIENTES, body())
			.log("Buscar cidade e estado")
			.to(CorreiosApiRoute.URI)
			.log("Salvando clientes...")
			.to(SqlRoute.INSERT_URI)
		.end();
		
	}

}
