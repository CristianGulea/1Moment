package ro.moment.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MessageDto extends BaseEntityDto {
    private Long userId;
    private Long groupId;
    private String title;
    private String content;
    private LocalDateTime publishDate;

    public MessageDto(Message entity) {
        super(entity);

        if (entity != null) {
            this.userId = entity.getUser().getId();
            this.groupId = entity.getGroup().getId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.publishDate = entity.getPublishDate();
        }
    }

    public MessageDto() {
    }

    public Message toDomain() {
        User user = new User();
        user.setId(userId);
        Group group = new Group();
        group.setId(groupId);
        Message message = new Message();
        message.setId(getId());
        message.setExternalId(getExternalId());
        message.setUpdatedDate(getUpdatedDate());
        message.setCreatedDate(getCreatedDate());
        message.setUser(user);
        message.setGroup(group);
        message.setTitle(title);
        message.setContent(content);
        message.setPublishDate(publishDate);

        return message;
    }
}
