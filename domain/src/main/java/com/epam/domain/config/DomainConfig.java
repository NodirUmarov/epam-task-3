package com.epam.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/27/2022
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.epam.domain", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EntityScan(basePackages = "com.epam.domain")
@EnableJpaAuditing
public class DomainConfig {



}
