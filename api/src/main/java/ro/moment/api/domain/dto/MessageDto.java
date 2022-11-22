package ro.moment.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.User;

@Getter
@Setter
@AllArgsConstructor
public class MessageDto extends BaseEntityDto {
    private String username;
    private String groupName;
    private String title;
    private String content;

    public MessageDto(Message entity) {
        super(entity);

        if (entity != null) {
            this.username = entity.getUser().getUsername();
            this.groupName = entity.getGroup().getName();
            this.title = entity.getTitle();
            this.content = entity.getContent();
        }
    }

    public MessageDto() {
    }

    public Message toDomain() {
        User user = new User();
        user.setUsername(username);
        Group group = new Group();
        group.setName(groupName);
        Message message = new Message();
        message.setId(getId());
        message.setExternalId(getExternalId());
        message.setUpdatedDate(getUpdatedDate());
        message.setCreatedDate(getCreatedDate());
        message.setUser(user);
        message.setGroup(group);
        message.setTitle(title);
        message.setContent(content);

        return message;
    }
}
