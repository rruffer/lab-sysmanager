package br.com.rruffer.lab.sysmanager.route.api;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.rruffer.lab.sysmanager.route.SqlRoute;

@Component
public class ClienteApiRestRoute extends RouteBuilder {

	public static final String CLIENTE_GET = "{{route.rest.get.cliente.id}}";

	public static final String URI_BASE = "/cliente";

	@Override
	public void configure() throws Exception {

		rest(URI_BASE).description("Rest service on IBMS Music API to deal with PrecueSheet Person")

		.get()
			.description("Retrieve Person Content by title name")
			.produces(MediaType.APPLICATION_JSON)
			.param()
				.description("Filtrar por cidade.")
				.type(RestParamType.query)
				.dataType("string")
				.name("cidade")
				.required(true)
			.endParam()
			.param()
				.description("Filtrar por estado.")
				.type(RestParamType.query)
				.dataType("string")
				.name("estado")
				.required(true)
			.endParam()
			.param()
				.description("Filtrar por faixa de idade minima.")
				.type(RestParamType.query)
				.dataType("string")
				.name("minIdade")
				.required(true)
				.endParam()
			.param()
				.description("Filtrar por faixa de idade máxima.")
				.type(RestParamType.query)
				.dataType("string")
				.name("maxIdade")
				.required(true)
			.endParam()
			.route()
				.routeId(CLIENTE_GET)
				.log("Buscar clientes pelo filtros [cidade: ${header.cidade}, estado: ${header.estado}, minIdade: ${header.minIdade}, maxIdade: ${header.maxIdade}]")
				.to(SqlRoute.SELECT_URI)
				/*.choice()
					.when(body().isLessThan(1))
						.setHeader(Exchange.HTTP_RESPONSE_CODE, HttpServletResponse.SC_NO_CONTENT)
				.end()*/
		.endRest();
	}
}
