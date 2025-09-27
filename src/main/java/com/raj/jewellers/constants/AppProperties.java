package com.raj.jewellers.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "raj.app")
public class AppProperties {

    private String docPathPrefix;
}
