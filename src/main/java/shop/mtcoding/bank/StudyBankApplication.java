package shop.mtcoding.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StudyBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyBankApplication.class, args);
    }

}
