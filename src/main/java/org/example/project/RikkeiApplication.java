package org.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
public class RikkeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RikkeiApplication.class, args);
    }

}
