package com.deliverytech.delivery.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperCoop {
    @Bean
    public ModelMapper modelMapperCooop() {
        return new ModelMapper();
    }
}
