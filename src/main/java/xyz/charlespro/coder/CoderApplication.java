package xyz.charlespro.coder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xyz.charlespro.coder.mapper")
public class CoderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoderApplication.class, args);
        System.out.println("Hello world!");
    }
}
