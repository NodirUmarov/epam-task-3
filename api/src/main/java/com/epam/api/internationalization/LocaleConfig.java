package com.epam.api.internationalization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/16/2022
 */
@Configuration
public class LocaleConfig {

    @Value("${spring.messages.basename}")
    private String propertiesBasename;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource rs = new ReloadableResourceBundleMessageSource();
        rs.setBasename("classpath:" + propertiesBasename);
        rs.setCacheSeconds(3600);
        rs.setDefaultEncoding("UTF-8");
        return rs;
    }
}
