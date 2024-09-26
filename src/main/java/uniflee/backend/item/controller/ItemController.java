package uniflee.backend.item.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uniflee.backend.item.dto.ItemRequestDto;
import uniflee.backend.item.dto.ItemUpdateRequest;
import uniflee.backend.item.dto.OwnItemResponse;
import uniflee.backend.item.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Tag(name = "ItemController", description = "상품 관리 API")
public class ItemController {
	private final ItemService itemService;

	@GetMapping
	@Operation(
			summary = "상품 목록을 조회합니다.",
			description = "상품 목록을 조회합니다."
	)
	public ResponseEntity<List<OwnItemResponse>> getOwnItems(){
		return ResponseEntity.ok(itemService.getOwnItems());
	}

	@PostMapping
	@Operation(
			summary = "상품을 생성합니다.",
			description = "상품을 생성합니다."
	)
	public ResponseEntity<?> createItem(@RequestBody ItemRequestDto request){
		itemService.createItem(request);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{itemId}")
	@Operation(
			summary = "상품을 수정합니다.",
			description = "상품을 수정합니다."
	)
	public ResponseEntity<?> updateItem(@RequestBody ItemUpdateRequest request, @PathVariable Long itemId){
		itemService.updateItem(request, itemId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	@Operation(
			summary = "상품을 삭제합니다.",
			description = "상품을 삭제합니다."
	)
	public ResponseEntity<?> deleteItem(@RequestBody @Schema(example = "[1,2,3]") List<Long> productIds){
		itemService.deleteItem(productIds);
		return ResponseEntity.ok().build();
	}
}
