package uniflee.backend.Recycling.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uniflee.backend.Recycling.domain.ItemType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "재활용 내역 목록 DTO")
public class RecyclingResponseDto {
    @Schema(description = "재활용품 종류", example = "PP")
    private ItemType itemType;
    @Schema(description = "재활용품 배출 개수", example = "1")
    private Long count;
    @Schema(description = "재활용품 적립 포인트", example = "10")
    private Long point;
}
