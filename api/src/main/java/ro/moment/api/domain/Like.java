package ro.moment.api.domain;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class Like extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;
}
