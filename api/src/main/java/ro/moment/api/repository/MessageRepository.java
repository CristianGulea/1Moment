package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findMessageByGroupId(Long id);

    List<Message> findMessagesByGroupName(String name);

    List<Message> findMessagesByParentMessageId(Long id);

}
