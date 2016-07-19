package com.csqlv;

import com.csqlv.model.QueryEntity;
import com.csqlv.parsers.QueryParser;
import com.csqlv.parsers.exceptions.ParseQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(DefaultConfig.class)
public class ApplicationRunner implements CommandLineRunner {

    private QueryParser queryParser;

    @Autowired
    public ApplicationRunner(QueryParser queryParser) {
        this.queryParser = queryParser;
    }

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}

    @Override
    public void run(String... args) {
        try {
            QueryEntity queryEntity = queryParser.parseQuery("SELECT COUNT(a), b FROM \"./somefile\" WHERE a<3 AND (b>2 OR a>1) ORDER BY a DESC LIMIT 10");
        } catch (ParseQueryException e) {
            System.out.println("There have been a problem with parsing query. Details: " + e.getMessage());
        }
    }
}
