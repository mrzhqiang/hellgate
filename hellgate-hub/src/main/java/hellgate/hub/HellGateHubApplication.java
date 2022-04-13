package hellgate.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("hellgate.**")
@EnableJpaRepositories("hellgate.**")
@ComponentScan("hellgate.**")
@SpringBootApplication
public class HellGateHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellGateHubApplication.class, args);
    }
}
