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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uniflee.backend.item.dto.ItemRequestDto;
import uniflee.backend.item.dto.ItemUpdateRequest;
import uniflee.backend.item.dto.OwnItemDetailResponse;
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
		description = "상품 목록을 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공적으로 조회되었습니다.",
				content = @Content(
					mediaType = "application/json",
						schema = @Schema(implementation = OwnItemResponse.class),
					examples = @ExampleObject(
						value = "["
							+ "{"
							+ "\"id\": 1, "
							+ "\"featuredImageUrl\": \"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\", "
							+ "\"designerName\": \"홍길동\", "
							+ "\"name\": \"상품1\", "
							+ "\"price\": 10000"
							+ "},"
							+ "{"
							+ "\"id\": 2, "
							+ "\"featuredImageUrl\": \"productImage/b7423289-09af-4b1a-b123-3c5bf9872347.png\", "
							+ "\"designerName\": \"김영희\", "
							+ "\"name\": \"상품2\", "
							+ "\"price\": 15000"
							+ "}"
							+ "]"
					)
				)
			)
		}
	)
	public ResponseEntity<List<OwnItemResponse>> getOwnItems(){
		return ResponseEntity.ok(itemService.getOwnItems());
	}

	@GetMapping("/{itemId}")
	@Operation(
		summary = "상품을 조회합니다.",
		description = "상품을 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공적으로 조회되었습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = OwnItemDetailResponse.class),
					examples = @ExampleObject(
						value = "{"
							+ "\"id\": 1, "
							+ "\"featuredImageUrl\": \"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\", "
							+ "\"designerName\": \"홍길동\", "
							+ "\"name\": \"상품1\", "
							+ "\"price\": 10000, "
							+ "\"descriptions\": ["
							+ "{"
							+ "\"description\": \"상품 설명입니다.\", "
							+ "\"imageUrl\": \"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\""
							+ "},"
							+ "{"
							+ "\"description\": \"상품 설명입니다.\", "
							+ "\"imageUrl\": \"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\""
							+ "}"
							+ "]"
							+ "}"
					)
				)
			)
		}
	)
	public ResponseEntity<OwnItemDetailResponse> getOwnItem(@PathVariable Long itemId){
		return ResponseEntity.ok(itemService.getOwnItem(itemId));
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
	public ResponseEntity<?> deleteItem(@RequestBody
	@Schema(name = "name", type = "array", example = "[1, 2, 3]") List<Long> productIds){
		itemService.deleteItem(productIds);
		return ResponseEntity.ok().build();
	}
}
