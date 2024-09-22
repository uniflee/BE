package uniflee.backend.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniflee.backend.orders.domain.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}