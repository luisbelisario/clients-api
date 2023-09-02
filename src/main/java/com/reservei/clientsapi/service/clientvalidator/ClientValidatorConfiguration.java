package com.reservei.clientsapi.service.clientvalidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ClientValidatorConfiguration {

    @Bean
    public List<ClientValidator> validators() {
        return Arrays.asList(
                new CpfValidator(),
                new EmailValidator(),
                new PublicIdValidator());
    }
}
