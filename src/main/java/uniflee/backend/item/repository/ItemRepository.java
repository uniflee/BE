package uniflee.backend.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniflee.backend.designer.domain.Designer;
import uniflee.backend.item.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByDesigner(Designer designer);
}
