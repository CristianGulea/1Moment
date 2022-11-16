package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
