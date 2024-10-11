package org.safetynet.alerts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AlertsApplication {

    public static void main(String[] args) {
        log.info("STARTING ALERTS APP");
        SpringApplication.run(AlertsApplication.class, args);
    }
}
