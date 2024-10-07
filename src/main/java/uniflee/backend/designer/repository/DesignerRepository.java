package uniflee.backend.designer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniflee.backend.designer.domain.Designer;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {
	public Optional<Designer> findByUsername(String username);
	public Boolean existsByUsername(String username);
}
