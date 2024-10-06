package uniflee.backend.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniflee.backend.orders.domain.Orders;
import uniflee.backend.user.domain.User;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByUserAndId(User user, Long Id);
}
