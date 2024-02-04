package mingle.chang.service.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private Info info() {
        return  new Info().title("Fer").description("Fer Project Api Doc").version("1.0.0");
    }

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(info());
    }
}
