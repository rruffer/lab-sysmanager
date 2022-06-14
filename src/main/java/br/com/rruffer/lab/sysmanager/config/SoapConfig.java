package br.com.rruffer.lab.sysmanager.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;

@Configuration
public class SoapConfig {
	
	@Value("${soap.service.address}")
	private String serviceAddress;
	
	@Bean("soapProducerEndpoint")
	public CxfEndpoint cxfEndpointVisitor(CamelContext camelContext) {
		CxfEndpoint cxfEndpoint = new CxfEndpoint();
		cxfEndpoint.setCamelContext(camelContext);
        cxfEndpoint.setAddress(serviceAddress);
        cxfEndpoint.setServiceClass(AtendeCliente.class);
        cxfEndpoint.setDataFormat(DataFormat.RAW);
        return cxfEndpoint;
	}

}
