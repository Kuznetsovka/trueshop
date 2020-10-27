package com.kuznetsovka.trueshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigServer
public class ConfigCloudServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigCloudServerApplication.class, args);
    }
}
