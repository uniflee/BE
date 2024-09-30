package uniflee.backend.Recycling.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uniflee.backend.Recycling.domain.ItemType;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecyclingResponseDto {
    private ItemType itemType;
    private Long count;
    private Long point;
}
