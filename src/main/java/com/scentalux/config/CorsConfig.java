package com.scentalux.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "https://scentalux-frontend.vercel.app",  // Tu frontend en Vercel
                    "http://localhost:9002",                  // Desarrollo local
                    "http://localhost:3000",                  // React dev server común
                    "http://localhost:5173"                   // Vite dev server común
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);  // ← Ahora SÍ con credentials
    }
}