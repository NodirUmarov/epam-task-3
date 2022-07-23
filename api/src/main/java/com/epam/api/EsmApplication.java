package com.epam.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/23/2022
 */
@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.epam.domain")
@EntityScan(basePackages = "com.epam.domain")
public class EsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsmApplication.class, args);
    }
}
