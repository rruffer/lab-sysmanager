package br.com.rruffer.lab.sysmanager.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import br.com.correios.bsb.sigep.master.bean.cliente.ConsultaCEP;
import br.com.correios.bsb.sigep.master.bean.cliente.ObjectFactory;
import br.com.rruffer.lab.sysmanager.util.AppConstantes;

@Component
public class ConsultaCepProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		ObjectFactory factory = new ObjectFactory();
		String cep = exchange.getProperty(AppConstantes.CEP, String.class);
		
		ConsultaCEP consultaCEP = factory.createConsultaCEP();
		consultaCEP.setCep(cep);
		
		exchange.getIn().setBody(consultaCEP);
		
	}

}
