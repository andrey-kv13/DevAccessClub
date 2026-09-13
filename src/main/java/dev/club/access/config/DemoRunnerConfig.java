package dev.club.access.config;

import dev.club.access.manager.ClubAccessManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.demo.enabled", havingValue = "true", matchIfMissing = true)
public class DemoRunnerConfig {

    @Bean
    CommandLineRunner demo(ClubAccessManager manager) {
        return args -> manager.runDemo();
    }
}
