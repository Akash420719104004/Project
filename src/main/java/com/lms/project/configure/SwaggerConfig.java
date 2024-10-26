package com.lms.project.configure;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {
    private String securitySchemeName = "Authorization";
    @Bean
    public OpenAPI customOpenAPI() {
//        License license=new License();
//        license.setName("My Project");
//        license.setUrl("Project");
//
//        Server localServer = new Server();
//        localServer.setUrl("http://localhost:8080/api");
//        localServer.setDescription("Server URL in Local environment");
//
//        final String apiTitle = String.format("%s API", StringUtils.capitalize(Constants.MYPROJECT_USER));
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .description("JWT Bearer token")
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                )


                );
//                .info(new Info().version("1.0").title(apiTitle).description("My Project").termsOfService("PROJECT_tool").license(license))
                //.servers(List.of(localServer));
    }

}
