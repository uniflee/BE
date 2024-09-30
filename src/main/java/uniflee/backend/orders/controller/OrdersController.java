package uniflee.backend.orders.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import uniflee.backend.orders.Dto.OrdersRequestDto;
import uniflee.backend.orders.service.OrdersService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {
	private final OrdersService ordersService;

	@PostMapping
	public ResponseEntity<String> addOrders(Authentication authentication, @RequestBody OrdersRequestDto ordersDto) {
		ordersService.addOrders(authentication.getName(), ordersDto.getItemId(), ordersDto.getCount());
		return ResponseEntity.ok("주문 완료");
	}
}
