package com.glimound.lottery;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Glimound
 */
@SpringBootApplication
@Configurable
public class LotteryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }
}
