package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.moment.api.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsLikeByUserIdAndMessageId(Long userId, Long messageId);

    Long countByMessageId(Long id);
}
