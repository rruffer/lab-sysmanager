package br.com.rruffer.lab.sysmanager.bean;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Body;

import br.com.rruffer.lab.sysmanager.domain.Cliente;

public class CreateListCepBean {
	
    public List<String> getDados(@Body List<Cliente> clientes) {
        return clientes.stream().map(Cliente::getCep).distinct().collect(Collectors.toList());
    }

}
