package uniflee.backend.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uniflee.backend.item.dto.ItemRequestDto;
import uniflee.backend.item.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {
	private final ItemService itemService;

	@PostMapping
	public ResponseEntity<?> createItem(@RequestBody ItemRequestDto request){
		itemService.createItem(request);
		return ResponseEntity.ok().build();
	}
}
