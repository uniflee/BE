package uniflee.backend.itemDescription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniflee.backend.itemDescription.domain.ItemDescription;

@Repository
public interface ItemDescriptionRepository extends JpaRepository<ItemDescription, Long> {
}
