package com.csqlv;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan
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

    private Path getResourcePath(String name) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(FileSystems.getDefault().getSeparator() + name);
        Path resPath = Paths.get(url.toURI());
        return resPath;
    }
}
