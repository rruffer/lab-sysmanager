package br.com.rruffer.lab.sysmanager.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.dataformat.soap.SoapJaxbDataFormat;
import org.apache.camel.dataformat.soap.name.ServiceInterfaceStrategy;
import org.apache.cxf.databinding.DataBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.ObjectFactory;

@Configuration
public class SoapConfig {
	
	public static final String TMS_DATA_FORMAT = "soapDataFormat";
	
	@Value("${soap.service.address}")
	private String serviceAddress;
	
	/*@Bean(TMS_DATA_FORMAT)
	public SoapJaxbDataFormat soapDataFormat() {
		SoapJaxbDataFormat soapJaxbDataFormat = 
				new SoapJaxbDataFormat(ObjectFactory.class.getPackage().getName(), new ServiceInterfaceStrategy(AtendeCliente.class, true));
		soapJaxbDataFormat.setPrettyPrint(false);
		soapJaxbDataFormat.setVersion("1.2");

		return soapJaxbDataFormat;
	}*/

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
