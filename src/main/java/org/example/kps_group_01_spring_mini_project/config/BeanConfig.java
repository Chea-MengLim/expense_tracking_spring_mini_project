package org.example.kps_group_01_spring_mini_project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
