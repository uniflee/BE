package uniflee.backend.orders.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrdersResponseDto {

    private Long id;
    private String name;
    private String designerName;
    private String featuredImageUrl;
    private Long count;
    private Long point;
    private Long totalPoint;


}
