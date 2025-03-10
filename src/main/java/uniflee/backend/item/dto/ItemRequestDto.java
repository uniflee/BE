package uniflee.backend.item.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 생성 요청 데이터 객체")
public class ItemRequestDto {
	@Schema(description = "상품의 대표 이미지 URL", example = "productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png")
	private String featuredImageUrl;
	@Schema(description = "상품 이름", example = "상품 이름")
	private String name;
	@Schema(description = "상품 가격", example = "10000")
	private Long price;
	@Schema(description = "상품 설명 목록", example = "[{\"imageUrl\":\"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\",\"description\":\"상품 설명\"},{\"imageUrl\":\"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\",\"description\":\"상품 설명\"}]")
	private List<ItemDescription> descriptions;




}
