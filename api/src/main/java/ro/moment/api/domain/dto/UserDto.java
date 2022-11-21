package ro.moment.api.domain.dto;

import lombok.Getter;
import lombok.Setter;
import ro.moment.api.domain.BaseEntity;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.User;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserDto extends BaseEntityDto{
    private String username;
    private String password;

    public UserDto(Long id, LocalDate createdDate, LocalDate updatedDate, UUID externalId, String username) {
        super(id, createdDate, updatedDate, externalId);
        this.username = username;
    }

    public UserDto(User entity) {
        super(entity);

        if (entity != null) {
            this.username = entity.getUsername();
        }
    }

    public UserDto() {
    }

    public User toDomain() {
        User user = new User();
        user.setId(getId());
        user.setExternalId(getExternalId());
        user.setUpdatedDate(getUpdatedDate());
        user.setCreatedDate(getCreatedDate());
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
