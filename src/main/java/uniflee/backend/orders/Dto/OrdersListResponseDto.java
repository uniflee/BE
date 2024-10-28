package uniflee.backend.orders.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "주문 내역 리스트 응답 DTO")
public class OrdersListResponseDto {
    @Schema(description = "유저가 현재 가지고 있는 포인트", example = "10000")
    private Long currentPoint;
    @Schema(description = "주문 내역 리스트", example = "[{\"id\": 12345, \"name\": \"Stylish Chair\", \"designerName\": \"John Doe\", \"featuredImageUrl\": \"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\", \"count\": 10, \"point\": 500, \"totalPoint\": 5000}, {\"id\": 12346, \"name\": \"Modern Lamp\", \"designerName\": \"Jane Smith\", \"featuredImageUrl\": \"productImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png\", \"count\": 5, \"point\": 200, \"totalPoint\": 1000}]")
    private List<OrdersResponseDto> ordersResponseDtoList;
}
