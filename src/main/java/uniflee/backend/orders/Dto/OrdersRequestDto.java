package uniflee.backend.orders.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersRequestDto {

    private Long itemId;
    private Long count;

}
