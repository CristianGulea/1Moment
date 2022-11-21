package ro.moment.api.domain.dto;

import lombok.Getter;
import lombok.Setter;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.User;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class MessageDto extends BaseEntityDto{
    private UserDto user;
    private GroupDto group;
    private String title;
    private String content;

    public MessageDto(Long id, LocalDate createdDate, LocalDate updatedDate, UUID externalId, User user, Group group, String title, String content) {
        super(id, createdDate, updatedDate, externalId);
        this.user = new UserDto(user);
        this.group = new GroupDto(group);
        this.title = title;
        this.content = content;
    }

    public MessageDto(Message entity) {
        super(entity);

        if(entity != null) {
            this.user = new UserDto(entity.getUser());
            this.group = new GroupDto(entity.getGroup());
            this.title = entity.getTitle();
            this.content = entity.getContent();
        }
    }

    public MessageDto() {
    }

    public Message toDomain() {
        Message message = new Message();
        message.setId(getId());
        message.setExternalId(getExternalId());
        message.setUpdatedDate(getUpdatedDate());
        message.setCreatedDate(getCreatedDate());
        message.setUser(user.toDomain());
        message.setGroup(group.toDomain());
        message.setTitle(title);
        message.setContent(content);

        return message;
    }
}
