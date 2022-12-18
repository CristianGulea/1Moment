package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByGroupId(Long id);
    List<Message> findByPublishDateBefore(LocalDateTime publishDate);
    List<Message> findByPublishDateBeforeAndGroup_Id(LocalDateTime publishDate, Long id);

}
