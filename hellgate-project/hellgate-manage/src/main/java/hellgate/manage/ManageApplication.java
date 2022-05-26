package hellgate.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@EntityScan("hellgate.**")
@EnableJpaRepositories("hellgate.**")
@ComponentScan("hellgate.**")
@Controller
@SpringBootApplication
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

    @GetMapping({"/", "/index", "/admin"})
    public String index() {
        return "home";
    }
}