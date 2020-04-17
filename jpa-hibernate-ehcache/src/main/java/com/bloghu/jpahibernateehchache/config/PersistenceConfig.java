package com.bloghu.jpahibernateehchache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.bloghu.jpahibernateehchache.repository")
public class PersistenceConfig {

}
