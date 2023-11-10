package com.productservice.core.util;


import org.springframework.context.annotation.Bean;

public class Randomizer {
    @Bean
    public static int generate(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
