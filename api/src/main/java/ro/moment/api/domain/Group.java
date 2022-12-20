package ro.moment.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity (name = "moment_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Group extends BaseEntity{
    private String name;

}
