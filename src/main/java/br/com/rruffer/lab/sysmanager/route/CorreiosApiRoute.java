package br.com.rruffer.lab.sysmanager.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rruffer.lab.sysmanager.aggregator.CepAggregationStrategy;
import br.com.rruffer.lab.sysmanager.bean.CreateListCepBean;
import br.com.rruffer.lab.sysmanager.util.AppConstantes;

@Service
public class CorreiosApiRoute extends RouteBuilder {
	
	public static final String ID = "{{route.correios.api.id}}";
	public static final String URI = "{{route.correios.api.uri}}";
	private static final String OPERATION_NAME_CONSULTA_CEP = "{{soap.service.operation.name.consulta.cep}}";
	public static final String SOAP_SERVICE_URI = "{{route.soap.address.uri}}";
	private static final String CONTENT_TYPE = "text/xml;charset=UTF-8";

	@Autowired
	private CepAggregationStrategy cepAggregationStrategy;
	
	@Override
	public void configure() throws Exception {
		
		from(URI)
			.routeId(ID)
			.streamCaching()
			.bean(CreateListCepBean.class)
			.split(body(), cepAggregationStrategy)
				.setProperty(AppConstantes.CEP, simple("${body}"))
				.log("buscar estado e cidade do cep: ${header.cep}")
				.setBody().simple("resource:classpath:/request/consultarCep.xml")
				.transform(simple("${body.replace('PARAM_CEP', ${header.cep})}"))
				.setHeader(Exchange.CONTENT_TYPE, constant(CONTENT_TYPE))
				.setHeader(CxfConstants.OPERATION_NAME, constant(OPERATION_NAME_CONSULTA_CEP))
				.to(SOAP_SERVICE_URI)
		        .convertBodyTo(String.class)
		        .transform().xpath("//*[local-name()='return']")
		        .setProperty(AppConstantes.CIDADE, xpath("/return/cidade/text()"))
		        .setProperty(AppConstantes.ESTADO, xpath("/return/uf/text()"))
				.log(">>> cidade: ${header.cidade}, estado: ${header.estado}")
			.end()
			.to("direct:insert-cliente")
		.end();
		
		from("direct:insert-cliente")
			.routeId("insert-cliente-id")
			.log("salvando clientes...")
			.split(exchangeProperty(AppConstantes.CLIENTES))
				.to("sql:classpath:sql/insertCliente.sql")
			.end()
			.log("clientes salvos...")
		.end();
		
	}

}
