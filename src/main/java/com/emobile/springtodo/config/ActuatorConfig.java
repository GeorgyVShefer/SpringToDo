package com.emobile.springtodo.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Map;

@Configuration
public class ActuatorConfig {

    @Bean
    public InfoContributor appInfoContributor() {
        return builder -> builder.withDetail("application", Map.of(
                "name", "My Spring Boot App",
                "version", "1.0.0",
                "description", "Custom Spring Boot Application"
        ));
    }

    @Bean
    public InfoContributor environmentInfoContributor(Environment env) {
        return builder -> builder.withDetail("environment", Map.of(
                "activeProfiles", Arrays.asList(env.getActiveProfiles()),
                "javaVersion", System.getProperty("java.version"),
                "os", System.getProperty("os.name")
        ));
    }
}
