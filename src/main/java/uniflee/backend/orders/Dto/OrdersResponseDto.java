package uniflee.backend.orders.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "주문 내역 리스트에 들어갈 정보")
public class OrdersResponseDto {
    @Schema(description = "orderId", example = "1")
    private Long id;
    @Schema(description = "item 이름", example = "desk")
    private String name;
    @Schema(description = "Designer 이름", example = "김선미")
    private String designerName;
    @Schema(description = "item 이미지", example = "productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png")
    private String featuredImageUrl;
    @Schema(description = "item 개수", example = "1")
    private Long count;
    @Schema(description = "item 가격", example = "10000")
    private Long point;
    @Schema(description = "item 가격 * 개수", example = "20000")
    private Long totalPoint;
    @Schema(description = "주문 등록 일시")
    private LocalDateTime createdAt;
}
