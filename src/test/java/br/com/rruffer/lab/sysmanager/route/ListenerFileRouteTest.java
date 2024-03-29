package br.com.rruffer.lab.sysmanager.route;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.PropertyInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import br.com.rruffer.lab.sysmanager.util.AppConstantes;
import br.com.rruffer.lab.sysmanager.util.ResourceReader;
import br.com.rruffer.lab.sysmanager.util.RouteUtil;

@SpringBootTest
@CamelSpringBootTest
@TestPropertySource({"classpath:mock.properties"})
@DisplayName("Test da rota ListenerFileRoute")
class ListenerFileRouteTest {
	
	@Autowired
	protected CamelContext camelContext;
	
	@Produce(ListenerFileRoute.URI)
	private ProducerTemplate producerTemplate;

	@Autowired
	protected RouteUtil routeUtil;

	@Value("classpath:samples/cliente/clientes.json")
	private Resource payloadClientes;
	
	@EndpointInject("{{route.soap.address.uri}}")
	private MockEndpoint mockCorreiosApi;

	@EndpointInject("{{route.sql.query.insert.uri}}")
	private MockEndpoint mockSqlInsert;

	@PropertyInject(value = CorreiosApiRoute.ID)
	private String correiosApiId;

	@PropertyInject(value = SqlRoute.INSERT_ID)
	private String sqlInsertId;
	
	@BeforeEach
	private void setup() throws Exception {
		//AdviceWith.adviceWith(camelContext, correiosApiId, false, route -> routeUtil.mockRoute(route, mockCorreiosApi, CorreiosApiRoute.SOAP_SERVICE_URI));
		//AdviceWith.adviceWith(camelContext, sqlInsertId, false, route -> routeUtil.mockRoute(route, mockSqlInsert, SqlRoute.INSERT_QUERY_URI));
	}
	
	@AfterEach
	private void clean() {
		mockCorreiosApi.reset();
		mockSqlInsert.reset();
	}
	
	@Test
	@DisplayName("Salvando clientes com sucesso")
	void saveClientsSuccess() throws Exception {
		
		mockCorreiosApi.whenAnyExchangeReceived(p -> {
			String cep = p.getProperty(AppConstantes.CEP, String.class).replace("-", "");
			p.getIn().setBody(ResourceReader.asString(new ClassPathResource("samples/correios/result-cep-"+ cep +".xml")));
		});
		
		startRoute(payloadClientes.getFile());
		
		mockCorreiosApi.expectedMessageCount(1);
		mockCorreiosApi.assertIsSatisfied(500);

		mockSqlInsert.expectedMessageCount(1);
		mockSqlInsert.assertIsSatisfied(500);
		
	}
	
	/*private Exchange startRoute(File payload, String... proprerties) {
		var exchangeIn = ExchangeBuilder.anExchange(camelContext)
				.withBody(payload)
				.withHeader("CamelFileNameConsumed", payload.getName())
				.build();
		return producerTemplate.send(exchangeIn);
	}*/
	
	private void startRoute(File payload) {
		producerTemplate.sendBodyAndHeader(payload, "CamelFileNameConsumed", payload.getName());
	}

}
