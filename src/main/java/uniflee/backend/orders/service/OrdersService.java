package uniflee.backend.orders.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;
import uniflee.backend.item.domain.Item;
import uniflee.backend.item.repository.ItemRepository;
import uniflee.backend.orders.domain.Orders;
import uniflee.backend.orders.repository.OrdersRepository;
import uniflee.backend.user.Repository.UserRepository;
import uniflee.backend.user.Service.UserService;
import uniflee.backend.user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

	private final OrdersRepository ordersRepository;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;
	private final UserService userService;

	@Transactional
	public void addOrders(String username, Long itemId, Long count) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));
		Item item = itemRepository.findById(itemId).orElseThrow(
				() -> new CustomException(ErrorCode.ITEM_NOT_FOUND_ERROR));

		userService.updatePoints(user, 0L, count * item.getPrice());
		ordersRepository.save(Orders.builder()
				.count(count)
				.item(item)
				.user(user)
				.build());
	}
}
