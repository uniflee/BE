package uniflee.backend.Refresh.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniflee.backend.Refresh.domain.RefreshToken;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefreshTokenAndUsername(String refreshToken, String username);
    void deleteByUsername(String username);
}