package br.com.rruffer.lab.sysmanager.route.api;

import javax.servlet.http.HttpServletResponse;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.rruffer.lab.sysmanager.route.SqlRoute;

@Component
public class ClienteApiRestRoute extends RouteBuilder {

	public static final String CLIENTE_GET = "{{route.rest.get.cliente.id}}";

	public static final String URI_BASE = "/cliente";

	@Override
	public void configure() throws Exception {

		rest(URI_BASE).description("Api Clientes Lab Sysmanager")

		.get()
			.description("Consulta de Clientes Lab Sysmanager")
			.produces(MediaType.APPLICATION_JSON.getType())
			.responseMessage(HttpServletResponse.SC_OK, "Retorna uma lista de clientes")
			.responseMessage(HttpServletResponse.SC_NO_CONTENT, "Retorna nenhum cliente")
			.param()
				.description("Filtrar por cidade.")
				.type(RestParamType.query)
				.dataType(String.class.getName())
				.name("cidade")
				.required(false)
			.endParam()
			.param()
				.description("Filtrar por estado.")
				.type(RestParamType.query)
				.dataType(String.class.getName())
				.name("estado")
				.required(false)
			.endParam()
			.param()
				.description("Filtrar por faixa de idade minima.")
				.type(RestParamType.query)
				.dataType(String.class.getName())
				.name("minIdade")
				.required(false)
				.endParam()
			.param()
				.description("Filtrar por faixa de idade máxima.")
				.type(RestParamType.query)
				.dataType(String.class.getName())
				.name("maxIdade")
				.required(false)
			.endParam()
			.to(SqlRoute.SELECT_URI);
	}
}
