package com.raj.jewellers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.raj.jewellers")
public class JewellersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JewellersApplication.class, args);
    }

}
