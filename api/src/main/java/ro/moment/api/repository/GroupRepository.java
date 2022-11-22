package ro.moment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.moment.api.domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findByName(String name);
}
