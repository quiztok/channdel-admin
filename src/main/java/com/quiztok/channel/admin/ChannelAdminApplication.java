package com.quiztok.channel.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.quiztok"})
@SpringBootApplication
public class ChannelAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run (ChannelAdminApplication.class, args);
    }

}
