package br.com.rruffer.lab.sysmanager.route;

import javax.servlet.http.HttpServletResponse;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import br.com.rruffer.lab.sysmanager.util.AppConstantes;

@Component
public class SqlRoute extends RouteBuilder {
	
	public static final String INSERT_ID = "{{route.sql.insert.id}}";
	public static final String INSERT_URI = "{{route.sql.insert.uri}}";

	public static final String SELECT_ID = "{{route.sql.select.id}}";
	public static final String SELECT_URI = "{{route.sql.select.uri}}";

	public static final String INSERT_QUERY_URI = "{{route.sql.query.insert.uri}}";
	
	public static final String SELECT_QUERY_URI = "{{route.sql.query.select.uri}}";

	@Override
	public void configure() throws Exception {
		
		from(INSERT_URI)
			.routeId(INSERT_ID)
			.split(exchangeProperty(AppConstantes.CLIENTES))
				.to(INSERT_QUERY_URI)
			.end()
			.log("Clientes salvos!")
		.end();

		from(SELECT_URI)
			.routeId(SELECT_ID)
			.log("Buscando clientes pelo filtros [cidade: ${header.cidade}, estado: ${header.estado}, minIdade: ${header.minIdade}, maxIdade: ${header.maxIdade}]")
			.to(SELECT_QUERY_URI)
			.log("Resultado: ${body}!")
			.filter(body().method("size").isEqualTo(0))
			.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(HttpServletResponse.SC_NO_CONTENT))
		.end();
		
	}
	
}
