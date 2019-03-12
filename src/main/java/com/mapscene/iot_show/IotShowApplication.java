package com.mapscene.iot_show;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class IotShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotShowApplication.class, args);
    }
}
