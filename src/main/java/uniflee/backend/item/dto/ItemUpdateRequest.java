package uniflee.backend.item.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 수정 요청 데이터 객체")
public class ItemUpdateRequest {
	@Schema(description = "상품 대표 이미지 URL", example = "https://example.com/featured.jpg")
	private String featuredImageUrl;
	@Schema(description = "상품 이름", example = "상품1")
	private String name;
	@Schema(description = "상품 가격", example = "10000")
	private Long price;
	@Schema(description = "상품 설명 목록")
	private List<ItemDescription> descriptions;
}
