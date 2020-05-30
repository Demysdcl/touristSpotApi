package com.restapi.touristspot.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .pathMapping("/")
            .apiInfo(apiInfo())


    fun apiInfo(): ApiInfo = ApiInfo(
            "Tourist Spots API",
            "Project find tourist spos",
            "Version 1.0",
            "",
            Contact("Demys Cota", "https://www.linkedin.com/in/demys-lima/", "demysdcl@gmail.com"),
            "To copy and learning",
            "",
            listOf()
    )

}