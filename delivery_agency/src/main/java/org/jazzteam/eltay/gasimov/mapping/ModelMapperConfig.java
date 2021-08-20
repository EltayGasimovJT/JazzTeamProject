package org.jazzteam.eltay.gasimov.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {

    @Bean(value = "modelMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}