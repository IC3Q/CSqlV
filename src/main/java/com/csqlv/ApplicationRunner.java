package com.csqlv;

import com.csqlv.utils.UserMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@Import(DefaultConfig.class)
public class ApplicationRunner implements CommandLineRunner {

    private CSqlV cSqlV;

    @Autowired
    public ApplicationRunner(CSqlV cSqlV) {
        this.cSqlV = cSqlV;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

    @Override
    public void run(String... args) {
        CommandLinePropertySource properties = new SimpleCommandLinePropertySource(args);
        if (properties.containsProperty("help")) {
            UserMessages.printHelp();
        } else {
            if (properties.containsProperty("query")) {
                cSqlV.handleQuery(properties.getProperty("query"));
            } else {
                UserMessages.printNothingProvided();
            }
        }
    }
}
