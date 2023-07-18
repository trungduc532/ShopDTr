package com.shopdtr.web.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopdtr.common", "com.shopdtr.web.backend.user"})
public class ShopDtrWebBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopDtrWebBackendApplication.class, args);
    }

}
