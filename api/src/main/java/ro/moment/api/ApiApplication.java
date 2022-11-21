package ro.moment.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.moment.api.domain.User;
import ro.moment.api.repository.UserRepository;

import java.util.List;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "ro.moment.api.repository")
@EntityScan(basePackages = "ro.moment.api.domain")
public class ApiApplication {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner testing(UserRepository userRepository) {
        return (args) -> {
//            User user = new User("admin", encoder.encode("admin"),List.of("ADMIN"));
//            userRepository.save(user);

            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);
        };
    }
}
