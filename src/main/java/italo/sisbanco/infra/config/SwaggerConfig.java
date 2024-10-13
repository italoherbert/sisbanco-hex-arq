package italo.sisbanco.infra.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@Profile("!test") 
public class SwaggerConfig {

    private final String DESCRIPTION = "Trata-se de uma implementação de um simples sistema de banco feito utilizando arquitetura hexagonal.";
    private final String SERVER_DESCRIPTION = "Servidor local 8080";

    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.server.url}")
    private String appServerUrl;
    
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title( appName.toUpperCase() )
                    .version( appVersion )
                    .description( DESCRIPTION )                
            )                       
            .servers( 
                Collections.singletonList( 
                    new Server().url( appServerUrl ).description( SERVER_DESCRIPTION ) 
                ) 
            );
    }

}
