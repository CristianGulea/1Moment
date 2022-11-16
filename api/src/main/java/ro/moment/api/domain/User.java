package ro.moment.api.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity(name = "moment_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity{
    private String username;
    private String password;
}
