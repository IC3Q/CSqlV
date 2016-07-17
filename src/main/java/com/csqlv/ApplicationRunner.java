package com.csqlv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(DefaultConfig.class)
public class ApplicationRunner implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello, world.");
    }
}
