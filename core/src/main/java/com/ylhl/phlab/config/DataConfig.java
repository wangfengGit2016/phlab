package com.ylhl.phlab.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "data")
public class DataConfig {
    private String deleted;
    private String noDeleted;
    private String isDeleted;
    private String createTime;
    private String updateTime;
}
