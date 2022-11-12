package ro.moment.api.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{
    private String username;
    private String password;
}
