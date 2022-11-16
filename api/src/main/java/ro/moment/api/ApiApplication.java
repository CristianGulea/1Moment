package ro.moment.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.User;
import ro.moment.api.repository.GroupRepository;
import ro.moment.api.repository.UserRepository;
import ro.moment.api.controller.ControllerGroup;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ro.moment.api.repository")
@EntityScan(basePackages = "ro.moment.api.domain")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner testing(UserRepository userRepository) {
        return (args) -> {
            User user = new User("Test", "");
            userRepository.save(user);

            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);
        };
    }

    @Bean
    public CommandLineRunner testingControllerGroup(GroupRepository groupRepository) {
        return (args) -> {
            Group group = new Group("aAaA");
            Group group1 = new Group("a");
            Group group2 = new Group("2");
            Group group3 = new Group("AaAa");
            Group group4 = new Group("2a");
            Group group5 = new Group("a2");

            ControllerGroup controllerGroup = new ControllerGroup(groupRepository);

            controllerGroup.save(group);
            controllerGroup.save(group1);
            controllerGroup.save(group2);
            controllerGroup.save(group3);
            controllerGroup.save(group4);
            controllerGroup.save(group5);

            //This should print only "aAaA" and "AaAa"
            List<Group> groups = controllerGroup.findAll();
            groups.forEach(System.out::println);
        };
    }
}
