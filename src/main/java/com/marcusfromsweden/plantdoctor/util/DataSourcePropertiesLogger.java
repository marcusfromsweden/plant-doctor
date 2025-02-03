package com.marcusfromsweden.plantdoctor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourcePropertiesLogger implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSourcePropertiesLogger.class);

    private final DataSourceProperties dataSourceProperties;

    @Autowired
    public DataSourcePropertiesLogger(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("DataSource URL: {}", dataSourceProperties.getUrl());
        log.info("DataSource Username: {}", dataSourceProperties.getUsername());
        log.info("DataSource Password: {}", dataSourceProperties.getPassword());

    }
}