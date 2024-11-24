package com.laviprog.pastes.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfiguration {

    @Bean
    public String bucketDefault(@Value("${aws.bucket-default}") String bucketDefault) {
        return bucketDefault;
    }

}
