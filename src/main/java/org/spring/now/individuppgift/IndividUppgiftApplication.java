package org.spring.now.individuppgift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("model")
@ComponentScan(basePackages = {"org.spring.now.individuppgift", "controller", "service", "dao", "security", "model"})
public class IndividUppgiftApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndividUppgiftApplication.class, args);
    }

}
