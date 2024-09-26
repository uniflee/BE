package uniflee.backend.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 설명 데이터 객체")
public class ItemDescription {
	@Schema(description = "이미지 URL", example = "https://example.com/image.jpg")
	private String imageUrl;
	@Schema(description = "설명", example = "상품 설명")
	private String description;
}
