package org.wallerlab.jacob;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.wallerlab.jacob.config.BatchConfig;

@Import(BatchConfig.class)
@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
