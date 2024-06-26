package io.goji.biz.maner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi knife4jApi() {
        return GroupedOpenApi.builder()
                .group("tvh")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("TVH API")
                                .version("1.0")
                                .description("TVH API")
                                .contact(
                                        new Contact()
                                                .email("8888@gmail.com")
                                                .name("TVH")
                                )
                                .license(new License()
                                        .name("MIT")
                                        .url("https://opensource.org/licenses/MIT"))
                );
    }




}
