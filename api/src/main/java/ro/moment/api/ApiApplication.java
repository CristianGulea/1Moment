package ro.moment.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.moment.api.domain.*;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.repository.*;
import ro.moment.api.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "ro.moment.api.repository")
@EntityScan(basePackages = "ro.moment.api.domain")
public class ApiApplication {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);

    }

    private void initDB(UserRepository userRepo, GroupRepository groupRepo, MessageRepository messageRepo, SubscriptionRepository subscriptionRepository, LikeRepository likeRepository) {
        if (userRepo.count() >= 5) {
            return;
        }



        User user1 = new User("admin", encoder.encode("admin"),List.of("ADMIN"));
        User user2 = new User("Horea", encoder.encode("horea"),List.of("USER"));
        User user3 = new User("Bogdan", encoder.encode("bogdan"),List.of("USER"));
        User user4 = new User("Maria", encoder.encode("maria"),List.of("USER"));
        User user5 = new User("Ioan", encoder.encode("ioan"),List.of("USER"));

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        userRepo.save(user5);

        Group group1 = new Group("Grupa 233");
        Group group2 = new Group("Revelion 2022-2023");
        Group group3 = new Group("Proiect colectiv");
        Group group4 = new Group("Glume");
        Group group5 = new Group("UBB FMI");
        Group group6 = new Group("General");

        groupRepo.save(group1);
        groupRepo.save(group2);
        groupRepo.save(group3);
        groupRepo.save(group4);
        groupRepo.save(group5);
        groupRepo.save(group6);



        Message m1 = new Message(user2, group1,null, "Semetrul I...", "Doar atat");
        Message m2 = new Message(user3, group1,null, "Atentie!", "Va multumesc pentru atentie!");
        Message m3 = new Message(user4, group1,null, "Tema aceasta", "Inca nu am terminat-o");
        Message m4 = new Message(user5, group2,null, "Anul viitor", "De abia astept urmatorul an");
        Message m5 = new Message(user5, group2,null, "Unde ne intalnim?", "");
        Message m6 = new Message(user5, group2,null, "Ne mai intalnim?", "Lumea nu pare prea activa pe aici...");
        Message m7 = new Message(user2, group3, null,"Workshop", "Urmatoarea intalnire va fi pe...");
        Message m8 = new Message(user3, group3,null, "Proiecte", "Imi plac proiectele tuturor");
        Message m9 = new Message(user2, group5,null, "PDM", "Ma ajutati cu tema?");
        Message m10 = new Message(user3, group5, null,"Banchet", "yey");
        Message m11 = new Message(user4, group5,null, "LFTC", "BISON");
        Message m12 = new Message(user5, group5,null, "PPD", "Thread");
        Message m13 = new Message(user4, group6, null,"Craciun", "Imi plac colindele");
        Message m14 = new Message(user4, group6,null, "[object Object]", "[object Object]");
        Message m15 = new Message(user4, group6,null, "{genericPostText}", "{genericPostBody}");




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


        Subscription s1 = new Subscription(user1,group1);
        Subscription s2 = new Subscription(user2,group1);
        Subscription s3 = new Subscription(user1, group2);
        Subscription s4 = new Subscription(user2, group2);
        Subscription s5 = new Subscription(user2, group3);

        subscriptionRepository.save(s1);
        subscriptionRepository.save(s2);
        subscriptionRepository.save(s3);
        subscriptionRepository.save(s4);
        subscriptionRepository.save(s5);

        Message m16 = new Message(user1, group1, m1, "TestParentM16", "TestParentM16");
        messageRepo.save(m16);

        Message m17 = new Message(user1, group1, m1, "TestParentM17", "TestParentM17");
        messageRepo.save(m17);

        Like l1 = new Like(user1, m17);
        Like l2 = new Like(user1, m16);
        Like l3 = new Like(user2, m17);

        likeRepository.save(l1);
        likeRepository.save(l2);
        likeRepository.save(l3);


    }

    @Bean

    public CommandLineRunner testing(UserRepository userRepo, GroupRepository groupRepo, MessageRepository messageRepo, SubscriptionRepository subscriptionRepo, LikeRepository likeRepo, MessageService messageService) {
        return (args) -> {
            //run if you want to populate the database
            //initDB(userRepo, groupRepo, messageRepo, subscriptionRepo, likeRepo);

            /*
            System.out.println(messageService.findMessagesByParenMessageId(12L).get(0).getContent());
            System.out.println("Test OK");

            List<MessageDto> messagesOfUser2 = messageService.findAllByUserIdDtos(2L);
            for(MessageDto m: messagesOfUser2)
            {
                System.out.println(m.getContent() + " | ");
            }

            System.out.println("Test OK! Afiseaza toate mesajele din grupurile in care este user 2 inscris (1,2,3) chiar daca mesajele nu sunt postate de el");



            List<MessageDto> mostPopulareMessagesShowForUser1 = messageService.mostPopularMessages(1L);
            for(MessageDto m: mostPopulareMessagesShowForUser1)
            {
                System.out.println(m.getContent() + " | ");
            }

            List<MessageDto> mostPopulareMessagesShowForUser2 = messageService.mostPopularMessages(2L);
            for(MessageDto m: mostPopulareMessagesShowForUser2)
            {
                System.out.println(m.getContent() + " | ");
            }

            System.out.println("Test ok! Afiseaza mesajul m17 care are 2 like-uri");


            //in caz ca lista e goala
            if (messageService.findMessagesByParenMessageId(15L).size() == 0){
                System.out.println("Da");
            }

            List<MessageDto> mostPopulareMessagesShowForUser5 = messageService.mostPopularMessages(5L);
            if (mostPopulareMessagesShowForUser5.size() == 0){
                System.out.println("Da x2");
            }

             */

        };
    }
}
