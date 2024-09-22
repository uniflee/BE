package uniflee.backend.orders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uniflee.backend.orders.service.OrdersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {
	private final OrdersService ordersService;
}
