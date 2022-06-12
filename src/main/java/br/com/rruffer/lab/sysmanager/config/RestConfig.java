package br.com.rruffer.lab.sysmanager.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig {
	
	@Value("${camel.springboot.name}")
	private String contextPath;
	
	
	@Bean
	ServletRegistrationBean restApiServlet() {
		final ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), String.format("/%s/*", contextPath));
		servlet.setName("CamelServlet");
		return servlet;
	}
	
	
}
