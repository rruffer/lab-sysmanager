package br.com.rruffer.lab.sysmanager.bean;

import br.com.correios.bsb.sigep.master.bean.cliente.ConsultaCEP;

public class ConsultaCepBean {
	
    public ConsultaCEP getDados(String cep) {
		ConsultaCEP request = new ConsultaCEP();
		request.setCep(cep);
        return request;
    }

}
