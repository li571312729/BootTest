package com.hefu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
public class BootServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootServiceApplication.class, args);
    }
}
