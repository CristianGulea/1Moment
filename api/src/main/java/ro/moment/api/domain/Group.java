package ro.moment.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity (name = "moment_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Group extends BaseEntity{
    private String name;
    @ManyToMany
    @JoinTable(
            name = "moment_user",
            joinColumns = @JoinColumn(name ="id"),
            inverseJoinColumns = @JoinColumn(name ="id"))
    private List<User> members;

}
