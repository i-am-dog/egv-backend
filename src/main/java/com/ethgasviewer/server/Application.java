package com.ethgasviewer.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static final String GRAB_PROFILE = "grab";
    public static final String WEB3_PROFILE = "web3";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
