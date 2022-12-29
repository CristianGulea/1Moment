package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsLikeByUserIdAndMessageId(Long userId, Long messageId);
    Long countByMessageId(Long id);
    Like getLikeByUserIdAndMessageId(Long userId, Long messageId);
}
