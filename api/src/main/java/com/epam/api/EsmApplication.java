package com.epam.api;

import com.epam.business.config.BusinessConfig;
import com.epam.domain.config.DomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/23/2022
 */
@EnableSwagger2
@EnableCaching
@SpringBootApplication(scanBasePackageClasses = {
        DomainConfig.class,
        EsmApplication.class,
        BusinessConfig.class
})
public class EsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsmApplication.class, args);
    }
}