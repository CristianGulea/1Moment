package ro.moment.api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message extends BaseEntity{

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne()
    @JoinColumn(name = "parent_message_id", referencedColumnName = "id")
    private Message parentMessage;

    private String title;
    private String content;
    private LocalDateTime publishDate;
}
