package org.vg.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final Contact DEFAULT_CONTACT = new Contact("Vishnu Gupta", "https://github.com/VishnuGupta16", "guptavishnunitsk@gmail.com");
	@SuppressWarnings("rawtypes")
	private static final ApiInfo DEFAULT = new ApiInfo("Api Documentation", "Spring Boot Rest Services Best Practices", "1.0.0", "No Conditions",
			DEFAULT_CONTACT, "VJ 1.0", "urn:tos", new ArrayList<VendorExtension>());
	private static final Set<String> CONTENT_TYPE = new HashSet<>(Arrays.asList("application/json","application/xml"));


	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT)
				.consumes(CONTENT_TYPE)
				.produces(CONTENT_TYPE)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.vg"))
				.build();
	}
}
