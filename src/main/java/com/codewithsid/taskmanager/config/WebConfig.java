package com.codewithsid.taskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:3000,http://localhost:4200}")
    private String allowedOriginsString;

    @Value("${cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}")
    private String allowedMethodsString;

    @Value("${cors.allowed-headers:*}")
    private String allowedHeadersString;

    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = allowedOriginsString.split(",");
        String[] methods = allowedMethodsString.split(",");
        String[] headers = allowedHeadersString.split(",");
        
        // Clean up any whitespace
        for (int i = 0; i < origins.length; i++) {
            origins[i] = origins[i].trim();
        }
        for (int i = 0; i < methods.length; i++) {
            methods[i] = methods[i].trim();
        }
        for (int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].trim();
        }

        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")  // More flexible than allowedOrigins
                .allowedMethods(methods)
                .allowedHeaders(headers)
                .allowCredentials(allowCredentials)
                .maxAge(3600);
    }
}
