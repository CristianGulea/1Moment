package ro.moment.api.domain.dto;

import lombok.Getter;
import lombok.Setter;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.User;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class GroupDto extends BaseEntityDto{
    private String name;
    private boolean liked;
    private long likeCount;
    private Set<User> users = new java.util.LinkedHashSet<>();

    public GroupDto(Long id, LocalDate createdDate, LocalDate updatedDate, UUID externalId, String name) {
        super(id, createdDate, updatedDate, externalId);
        this.name = name;
    }

    public GroupDto(Group entity) {
        super(entity);
        if (entity != null) {
            this.name = entity.getName();
            this.users = entity.getUsers();
        }
    }

    public GroupDto() {
    }

    public Group toDomain() {
        Group group = new Group();
        group.setId(getId());
        group.setExternalId(getExternalId());
        group.setUpdatedDate(getUpdatedDate());
        group.setCreatedDate(getCreatedDate());
        group.setName(getName());
        return group;
    }
}
