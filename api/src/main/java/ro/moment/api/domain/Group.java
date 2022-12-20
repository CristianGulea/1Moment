package ro.moment.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "moment_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Group extends BaseEntity{
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "moment_user_group",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new java.util.LinkedHashSet<>();

    //Adds a user to the many-to-many relationship
    //between User and Group
    public void addUser(User user){
        users.add(user);
        user.addGroup(this);
    }

}
