package com.csqlv.config;

import com.csqlv.parsers.SQLQueryParser;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableAutoConfiguration
public class TestConfig {

    @Bean(name = "ProperPath")
    public Path getProperPath() throws URISyntaxException {
        return getResourcePath("a.csv");
    }

    @Bean(name = "NonExistingPath")
    public Path getNonExistingPath() {
        return Paths.get("/0138ac89-f229-48fd-8185-9c986c662505");
    }

    @Bean
    public SQLQueryParser getSQLQueryParser() {
        return new SQLQueryParser(new TGSqlParser(EDbVendor.dbvmysql));
    }

    private Path getResourcePath(String name) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(name);
        Path resPath = Paths.get(url.toURI());
        return resPath;
    }
}
