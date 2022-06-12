package br.com.rruffer.lab.sysmanager.aggregator;

import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import br.com.rruffer.lab.sysmanager.domain.Cliente;
import br.com.rruffer.lab.sysmanager.util.AppConstantes;


@Component
public class CepAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		String cidade = newExchange.getProperty(AppConstantes.CIDADE, String.class);
		String estado = newExchange.getProperty(AppConstantes.ESTADO, String.class);
		String cep = newExchange.getProperty(AppConstantes.CEP, String.class);
		
		if(oldExchange == null ) {
			this.insertCidadeAndEstado(newExchange, cidade, estado, cep);
			return newExchange;
		}
		
		this.insertCidadeAndEstado(newExchange, cidade, estado, cep);

		return oldExchange;
	}

	@SuppressWarnings("unchecked")
	private void insertCidadeAndEstado(Exchange exchange, String cidade, String estado, String cep) {
		
		List<Cliente> clientes = exchange.getProperty(AppConstantes.CLIENTES, List.class);
		
		clientes.stream().filter(c -> c.getCep().equals(cep)).forEach(cliente -> {
			cliente.setCidade(cidade);
			cliente.setEstado(estado);
		});
		
		exchange.setProperty(AppConstantes.CLIENTES, clientes);
	}
		
}
