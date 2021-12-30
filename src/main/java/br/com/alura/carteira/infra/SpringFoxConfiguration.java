package br.com.alura.carteira.infra;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Carteira de Investimentos", 
	      "Cadastro de listagem de compra e venda de investimentos", 
	      "Termos de Uso", 
	      "Termos de Serviço", 
	      new Contact("Ralf Guth", "ralfguth.dev", "backend@ralfguth.dev"), 
	      "License of API", "API license URL", Collections.emptyList());
	}

}
