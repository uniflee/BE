package uniflee.backend.Recycling.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniflee.backend.Recycling.domain.Recycling;
import uniflee.backend.user.domain.User;

import java.util.List;

@Repository
public interface RecyclingRepository extends JpaRepository<Recycling, Long> {
    List<Recycling> findAllByUser(User user);
}
