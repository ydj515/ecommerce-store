package org.example.userservice.config.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLogConfig {

    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

}