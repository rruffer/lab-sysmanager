package br.com.rruffer.lab.sysmanager.route;

import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rruffer.lab.sysmanager.processor.ConsultaCepProcessor;
import br.com.rruffer.lab.sysmanager.util.AppConstantes;

@Service
public class CorreiosApiRoute extends RouteBuilder {
	
	public static final String ID = "{{route.correios.api.id}}";
	public static final String URI = "{{route.correios.api.uri}}";
	private static final String OPERATION_NAME_CONSULTA_CEP = "{{soap.service.operation.name.consulta.cep}}";
	public static final String SOAP_SERVICE_URI = "{{route.soap.address.uri}}";
	private static final String CONTENT_TYPE = "text/xml;charset=UTF-8";

	@Autowired
	private ConsultaCepProcessor consultaCepProcessor;
	
	@Override
	public void configure() throws Exception {
		
		from(URI)
			.routeId(ID)
			.split(body())
				.setProperty(AppConstantes.CEP, simple("${body.cep}"))
				//.process(consultaCepProcessor)
				.log("buscar estado e cidade do cep: ${header.cep}")
				.setBody().simple("resource:classpath:/request/consultarCep.txt")
				.setHeader(Exchange.CONTENT_TYPE, constant(CONTENT_TYPE))
				.setHeader(CxfConstants.OPERATION_NAME, constant(OPERATION_NAME_CONSULTA_CEP))
				.to(SOAP_SERVICE_URI)
				.log(">>> Body Request: ${body}")
			.end()
				
		.end();
		
	}

}