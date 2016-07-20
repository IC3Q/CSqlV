package com.csqlv;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class DefaultConfig {

    @Bean
    public TGSqlParser getTGSqlParser() {
        return new TGSqlParser(EDbVendor.dbvmysql);
    }

}
