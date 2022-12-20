package ro.moment.api.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "moment_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity{
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Group> groups = new java.util.LinkedHashSet<>();

    public Collection<? extends GrantedAuthority> findAuthorities() {
        return this.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //adds a group to the many-to-many relationship
    //between User and Group
    public void addGroup(Group group){
        groups.add(group);
    }

}
