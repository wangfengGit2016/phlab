package com.ylhl.phlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * 管理后台
 * @author zhengyq
 */

@EnableSwagger2
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
public class OmsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmsApiApplication.class, args);
    }

}
