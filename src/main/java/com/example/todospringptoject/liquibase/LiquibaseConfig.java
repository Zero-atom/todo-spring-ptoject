package com.example.todospringptoject.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    private final Environment env;

    @Autowired
    public LiquibaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);

        // Считываем значение sampledata из application.yaml
        boolean sampleDataEnabled = env.getProperty("spring.liquibase.sampledata", Boolean.class, false);

        if (sampleDataEnabled) {
            liquibase.setChangeLog("classpath:db/changelog/sampledata/insert-test-data.yaml");
        } else {
            liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
        }

        // Убедитесь, что Liquibase запускается
        liquibase.setShouldRun(true);

        return liquibase;
    }
}