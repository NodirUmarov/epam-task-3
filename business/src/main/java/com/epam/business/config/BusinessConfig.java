package com.epam.business.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/28/2022
 */
@Configuration
@EnableScheduling
@ComponentScan("com.epam.business")
@EnableFeignClients("com.epam.business")
public class BusinessConfig {
}
