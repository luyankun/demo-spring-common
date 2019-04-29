package com.demo.spring.common.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @Description:
 * @Author: 鲁砚琨
 * @Date: 2019/2/28 15:45
 * @Version: v1.0
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
