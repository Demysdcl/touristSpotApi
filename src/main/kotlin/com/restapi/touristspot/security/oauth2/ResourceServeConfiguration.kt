package com.restapi.touristspot.security.oauth2

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler

@Configuration
@EnableResourceServer
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources?.resourceId("restservice")
    }

    override fun configure(http: HttpSecurity?) {
        http!!
                .csrf().disable()
                .logout().logoutSuccessUrl("/").permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .antMatchers("/spots", "/index.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(OAuth2AccessDeniedHandler())
    }

}