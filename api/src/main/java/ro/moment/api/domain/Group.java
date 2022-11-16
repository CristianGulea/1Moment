package ro.moment.api.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity (name = "moment_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Group extends BaseEntity{
    private String name;
}
