package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
