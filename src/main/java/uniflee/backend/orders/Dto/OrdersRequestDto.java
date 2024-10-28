package uniflee.backend.orders.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "유저 주문 요청 DTO")
public class OrdersRequestDto {
    @Schema(description = "ItemId", example = "1")
    private Long itemId;
    @Schema(description = "Item 개수", example = "2")
    private Long count;

}
