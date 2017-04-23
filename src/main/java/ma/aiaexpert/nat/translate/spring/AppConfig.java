package ma.aiaexpert.nat.translate.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.aiaexpert.nat.translate.security.ActiveUserStore;

@Configuration
public class AppConfig {
    // beans

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

}