package uniflee.backend.orders.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrdersListResponseDto {

    private Long currentPoint;
    private List<OrdersResponseDto> ordersResponseDtoList;

}
