package uniflee.backend.orders.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniflee.backend.orders.repository.OrdersRepository;

@Service
@RequiredArgsConstructor
public class OrdersService {
	private final OrdersRepository ordersRepository;
}
