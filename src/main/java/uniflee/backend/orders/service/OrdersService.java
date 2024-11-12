package uniflee.backend.orders.service;

import static java.time.LocalDateTime.*;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniflee.backend.gcp.dto.OrderInfo;
import uniflee.backend.gcp.service.GoogleService;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;
import uniflee.backend.item.domain.Item;
import uniflee.backend.item.repository.ItemRepository;
import uniflee.backend.orders.Dto.OrdersListResponseDto;
import uniflee.backend.orders.Dto.OrdersResponseDto;
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
	private final GoogleService googleService;

	@Transactional
	public void addOrders(String username, Long itemId, Long count) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));
		Item item = itemRepository.findById(itemId).orElseThrow(
				() -> new CustomException(ErrorCode.ITEM_NOT_FOUND_ERROR));

		OrderInfo orderInfo = OrderInfo.builder()
				.id(user.getUsername())
				.ItemId(itemId)
				.orderDate(now())
				.count(count)
				.build();

		googleService.writeToSheet(orderInfo);

		userService.updatePoints(user, 0L,
			(long)(count * item.getPrice() * (1 - user.getGrade().getDiscountRate())));
		ordersRepository.save(Orders.builder()
				.count(count)
				.item(item)
				.user(user)
				.build());
	}

	public OrdersListResponseDto getOrders(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

		List<OrdersResponseDto> ordersResponseDtoList = user.getOrders().stream().map(orders ->
				OrdersResponseDto.builder()
						.id(orders.getId())
						.point(orders.getItem().getPrice())
						.name(orders.getItem().getName())
						.count(orders.getCount())
						.totalPoint(orders.getItem().getPrice() * orders.getCount())
						.designerName(orders.getItem().getDesigner().getName())
						.featuredImageUrl(orders.getItem().getFeaturedImageUrl())
						.build()).toList();

		return OrdersListResponseDto.builder()
				.currentPoint(user.getCurrentPoints())
				.name(user.getName())
				.ordersResponseDtoList(ordersResponseDtoList)
				.build();
	}

	public OrdersResponseDto getOrder(Long id, String username) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));
		Orders orders = ordersRepository.findByUserAndId(user, id).orElseThrow(
				() -> new CustomException(ErrorCode.NOT_FOUND_ORDER_ERROR));

		return OrdersResponseDto.builder()
				.id(orders.getId())
				.point(orders.getItem().getPrice())
				.name(orders.getItem().getName())
				.count(orders.getCount())
				.totalPoint(orders.getItem().getPrice() * orders.getCount())
				.designerName(orders.getItem().getDesigner().getName())
				.featuredImageUrl(orders.getItem().getFeaturedImageUrl())
				.build();
	}
}
