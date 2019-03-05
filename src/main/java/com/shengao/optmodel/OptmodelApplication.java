package com.shengao.optmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.shengao.optmodel.controller.FileUtil.writeToFile;

@SpringBootApplication
public class OptmodelApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptmodelApplication.class, args);
    }

}
