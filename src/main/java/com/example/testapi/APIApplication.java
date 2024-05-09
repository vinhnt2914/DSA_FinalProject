package com.example.testapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APIApplication {
    public static void main(String[] args) {
//        APIDataManager apiDataManager = APIDataManager.getInstance();
        SpringApplication.run(APIApplication.class, args);
    }
}
