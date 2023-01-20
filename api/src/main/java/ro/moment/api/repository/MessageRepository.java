package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    Message findMessageByIdAndPublishDateBefore(Long id, LocalDateTime publishDate);
    List<Message> findMessagesByParentMessageIdAndPublishDateBeforeOrderByIdDesc(Long parentMessage_id, LocalDateTime publishDate);
    List<Message> findByPublishDateBeforeOrderByIdDesc(LocalDateTime publishDate);
    List<Message> findByGroupIdAndPublishDateBeforeAndParentMessageIdOrderByIdDesc(Long group_id, LocalDateTime publishDate, Long parent_message_id);

}
