package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    List<Subscription> findAllByUserId(Long id);
    boolean existsByUserIdAndGroupId(Long user_id, Long group_id);
    Subscription getByUserIdAndGroupId(Long user_id, Long group_id);
}
