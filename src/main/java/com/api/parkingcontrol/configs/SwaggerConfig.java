package com.api.parkingcontrol.configs;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{

    // @Bean
    // public GroupedOpenApi publicApi() {
    //     return GroupedOpenApi.builder()
    //         .group("public-api")
    //         .pathsToMatch("/public/**")
    //         .build();
    // }

    // @Bean
    // public GroupedOpenApi api() {
    //     return GroupedOpenApi.builder()
    //         .group("com.api.parkingcontrol") // Substitua por um nome de grupo
    //         .pathsToMatch("**/**") // Substitua pelo pacote das suas APIs
    //         .build();
    // }

}
