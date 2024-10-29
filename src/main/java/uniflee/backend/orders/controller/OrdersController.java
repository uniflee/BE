package uniflee.backend.orders.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import uniflee.backend.orders.Dto.OrdersListResponseDto;
import uniflee.backend.orders.Dto.OrdersRequestDto;
import uniflee.backend.orders.Dto.OrdersResponseDto;
import uniflee.backend.orders.service.OrdersService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "OrdersController", description = "주문 관련 API")
public class OrdersController {
	private final OrdersService ordersService;

	@Operation(
			summary = "유저가 주문을 합니다.",
			description = "주문 Entity를 추가합니다."
	)
	@PostMapping
	public ResponseEntity<String> addOrders(Authentication authentication, @RequestBody OrdersRequestDto ordersDto) {
		ordersService.addOrders(authentication.getName(), ordersDto.getItemId(), ordersDto.getCount());
		return ResponseEntity.ok("주문 완료");
	}

	@Operation(
			summary = "주문 내역 목록을 가져옵니다.",
			description = "주문 내역 목록을 리스트로 가져옵니다."
	)
	@GetMapping
	public ResponseEntity<OrdersListResponseDto> getOrders(Authentication authentication) {
		OrdersListResponseDto ordersListResponseDto = ordersService.getOrders(authentication.getName());
		return ResponseEntity.ok(ordersListResponseDto);
	}

	@Operation(
			summary = "주문 내역을 가져옵니다.",
			description = "주문 내역을 특정한 하나만 가져옵니다."
	)
	@GetMapping("/order")
	public ResponseEntity<OrdersResponseDto> getOrder(Authentication authentication, @RequestParam Long id) {
		OrdersResponseDto ordersResponseDto = ordersService.getOrder(id, authentication.getName());
		return ResponseEntity.ok(ordersResponseDto);
	}
}
