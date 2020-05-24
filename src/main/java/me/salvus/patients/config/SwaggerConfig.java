package me.salvus.patients.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors
				.basePackage("me.salvus.patients"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Patient Rest API", "Patients rest api for Salvus with Spring Boot and h2 memory database",
				"v1", "termsOfServiceUrl", new Contact("Héber Ribeiro", "https://github.com/heberRibeiro", "heber.r@outlook.com"), "License MIT", "https://opensource.org/licenses/MIT",
				Collections.emptyList());
	}
}
