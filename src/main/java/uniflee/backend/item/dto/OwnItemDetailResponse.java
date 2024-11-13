package uniflee.backend.item.dto;

import static lombok.AccessLevel.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Schema(description = "상품 상세 조회 응답 데이터 객체")
public class OwnItemDetailResponse {
	@Schema(description = "상품 ID", example = "1")
	private Long id;
	@Schema(description = "상품 대표 이미지 URL", example = "productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png")
	private String featuredImageUrl;
	@Schema(description = "디자이너 이름", example = "홍길동")
	private String designerName;
	@Schema(description = "상품 이름", example = "상품1")
	private String name;
	@Schema(description = "상품 가격", example = "10000")
	private Long price;
	@ArraySchema(schema = @Schema(implementation = ItemDescriptionDto.class))
	private List<ItemDescriptionDto> descriptions;

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = PROTECTED)
	@Schema(description = "상품 설명 데이터 객체")
	public static class ItemDescriptionDto {
		@Schema(description = "상품 설명", example = "상품 설명입니다.")
		private String description;
		@Schema(description = "상품 이미지 URL", example = "productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png")
		private String imageUrl;
	}
}

