package jp.co.axa.apidemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@EnableCaching
@SpringBootApplication
public class ApiDemoApplication {

    public static void main(String[] args) {
        log.info("Starting application...");
        SpringApplication.run(ApiDemoApplication.class, args);
    }
}
