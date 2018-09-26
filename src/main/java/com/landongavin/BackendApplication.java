package com.landongavin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {
    public static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args)  {
        applicationContext = SpringApplication.run(BackendApplication.class, args);
	}

}
