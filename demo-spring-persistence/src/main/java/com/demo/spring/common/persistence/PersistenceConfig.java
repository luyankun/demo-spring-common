package com.demo.spring.common.persistence;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@MapperScan(basePackages = "${mybatis.mapper.scanner.path}")
@EnableTransactionManagement
@Configuration
public class PersistenceConfig {
}
