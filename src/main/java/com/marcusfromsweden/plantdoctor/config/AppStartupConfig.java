package com.marcusfromsweden.plantdoctor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupConfig {

    private static final Logger log = LoggerFactory.getLogger(AppStartupConfig.class);

    @EventListener
    public void handleApplicationStarting(ApplicationStartingEvent event) {
        log.debug("Relevant event - Application is starting: {}", event);
    }

    @EventListener
    public void handleApplicationEnvironmentPrepared(ApplicationEnvironmentPreparedEvent event) {
        log.debug("Relevant event - Application environment prepared: {}", event);
    }

    @EventListener
    public void handleApplicationPrepared(ApplicationPreparedEvent event) {
        log.debug("Relevant event - Application prepared: {}", event);
    }

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent event) {
        log.debug("Relevant event - Context refreshed: {}", event);
    }

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent event) {
        log.debug("Relevant event - Application started: {}", event);
    }

    @EventListener
    public void handleApplicationReady(ApplicationReadyEvent event) {
        log.debug("Relevant event - Application ready: {}", event);
    }
}