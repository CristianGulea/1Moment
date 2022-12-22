package ro.moment.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.repository.GroupRepository;
import ro.moment.api.repository.MessageRepository;
import ro.moment.api.repository.UserRepository;
import ro.moment.api.service.GroupService;
import ro.moment.api.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "ro.moment.api.repository")
@EntityScan(basePackages = "ro.moment.api.domain")
public class ApiApplication {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    private void initDB(UserRepository userRepo, GroupRepository groupRepo, MessageRepository messageRepo) {
        if (userRepo.count() >= 5) {
            return;
        }

        User user1 = new User("admin", encoder.encode("admin"),List.of("ADMIN"), Set.of());
        User user2 = new User("Horea", encoder.encode("horea"),List.of("USER"), Set.of());
        User user3 = new User("Bogdan", encoder.encode("bogdan"),List.of("USER"), Set.of());
        User user4 = new User("Maria", encoder.encode("maria"),List.of("USER"), Set.of());
        User user5 = new User("Ioan", encoder.encode("ioan"),List.of("USER"), Set.of());

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        userRepo.save(user5);

        Group group1 = new Group("Grupa 233", Set.of());
        Group group2 = new Group("Revelion 2022-2023", Set.of());
        Group group3 = new Group("Proiect colectiv", Set.of());
        Group group4 = new Group("Glume", Set.of());
        Group group5 = new Group("UBB FMI", Set.of());
        Group group6 = new Group("General", Set.of());

        groupRepo.save(group1);
        groupRepo.save(group2);
        groupRepo.save(group3);
        groupRepo.save(group4);
        groupRepo.save(group5);
        groupRepo.save(group6);

        Message m1 = new Message(user2, group1, "Semetrul I...", "Doar atat");
        Message m2 = new Message(user3, group1, "Atentie!", "Va multumesc pentru atentie!");
        Message m3 = new Message(user4, group1, "Tema aceasta", "Inca nu am terminat-o");
        Message m4 = new Message(user5, group2, "Anul viitor", "De abia astept urmatorul an");
        Message m5 = new Message(user5, group2, "Unde ne intalnim?", "");
        Message m6 = new Message(user5, group2, "Ne mai intalnim?", "Lumea nu pare prea activa pe aici...");
        Message m7 = new Message(user2, group3, "Workshop", "Urmatoarea intalnire va fi pe...");
        Message m8 = new Message(user3, group3, "Proiecte", "Imi plac proiectele tuturor");
        Message m9 = new Message(user2, group5, "PDM", "Ma ajutati cu tema?");
        Message m10 = new Message(user3, group5, "Banchet", "yey");
        Message m11 = new Message(user4, group5, "LFTC", "BISON");
        Message m12 = new Message(user5, group5, "PPD", "Thread");
        Message m13 = new Message(user4, group6, "Craciun", "Imi plac colindele");
        Message m14 = new Message(user4, group6, "[object Object]", "[object Object]");
        Message m15 = new Message(user4, group6, "{genericPostText}", "{genericPostBody}");

        messageRepo.save(m1);
        messageRepo.save(m2);
        messageRepo.save(m3);
        messageRepo.save(m4);
        messageRepo.save(m5);
        messageRepo.save(m6);
        messageRepo.save(m7);
        messageRepo.save(m8);
        messageRepo.save(m9);
        messageRepo.save(m10);
        messageRepo.save(m11);
        messageRepo.save(m12);
        messageRepo.save(m13);
        messageRepo.save(m14);
        messageRepo.save(m15);
    }

    public void test_addMemberToGroup(GroupRepository groupRepo, UserRepository userRepo, GroupService groupService){
        List<Group> groups = groupRepo.findAll();
        List<User> users = userRepo.findAll();

        groupService.addMember(new GroupDto(groups.get(1)), new UserDto(users.get(1)));
        groups = groupRepo.findAll();
        System.out.println(Arrays.toString(groups.get(1).getUsers().toArray()));
    }

    @Bean
    public CommandLineRunner testing(UserRepository userRepo, GroupRepository groupRepo, MessageRepository messageRepo, GroupService groupService, UserService userService) {
        return (args) -> {
            //run if you want to populate the database
            //initDB(userRepo, groupRepo, messageRepo);
            test_addMemberToGroup(groupRepo, userRepo, groupService);
        };
    }
}
