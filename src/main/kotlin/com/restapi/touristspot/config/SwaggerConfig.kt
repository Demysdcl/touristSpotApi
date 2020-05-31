package com.restapi.touristspot.config


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger.web.SecurityConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig(
        @Value("\${app.client.id}")
        val clientId: String,
        @Value("\${app.client.secret}")
        val clientSecret: String,
        @Value("\${host.full.dns.auth.link}")
        val authLink: String
) {

    @Bean
    fun securityConfiguration(): SecurityConfiguration =
            SecurityConfigurationBuilder.builder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .scopeSeparator(" ")
                    .useBasicAuthenticationWithAccessCodeGrant(true)
                    .build()


    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.restapi.touristspot.domain"))
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(listOf(securitySchema()))
            .securityContexts(listOf(securityContect()))
            .pathMapping("/")
            .apiInfo(apiInfo())


    fun apiInfo(): ApiInfo = ApiInfo(
            "Tourist Spots API",
            "Project find tourist spos",
            "Version 1.0",
            "",
            Contact("Demys Cota", "https://www.linkedin.com/in/demys-lima/", "demysdcl@gmail.com"),
            "",
            "",
            listOf()
    )

    fun securitySchema() = OAuth(
            "oauth2schema",
            arrayOfAuthorizationScopes().toList(),
            listOf(ResourceOwnerPasswordCredentialsGrant("$authLink/oauth/token"))
    )

    fun securityContect(): SecurityContext = SecurityContext.builder()
            .securityReferences(listOf(defaultAuth()))
            .forPaths(PathSelectors.ant("/users/**"))
            .build()

    private fun defaultAuth() = SecurityReference(
            "oauth2schema",
            arrayOfAuthorizationScopes()
    )

    private fun arrayOfAuthorizationScopes() = arrayOf(
            AuthorizationScope("read", "read all"),
            AuthorizationScope("write", "access all")
    )

}