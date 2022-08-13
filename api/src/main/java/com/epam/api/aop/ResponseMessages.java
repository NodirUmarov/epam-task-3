package com.epam.api.aop;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:internationalization/en.properties")
public class ResponseMessages {

    public ResponseMessages() {}

    @Value("${notFound}")
    public String notFound;

    @Value("${cannotDelete}")
    public String cannotDelete;

    @Value("${invalidFormat}")
    public String invalidFormat;

    @Value("${duplicateData}")
    public String duplicateData;
}