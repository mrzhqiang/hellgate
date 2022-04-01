package hellgate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("hellgate.**")
@EnableJpaRepositories("hellgate.**")
@ComponentScan("hellgate.**")
@SpringBootApplication
public class HellGateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellGateApiApplication.class, args);
    }
}
