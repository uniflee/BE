package uniflee.backend.Recycling.Dto;

import lombok.Getter;
import lombok.Setter;
import uniflee.backend.Recycling.domain.ItemType;

@Getter
@Setter
public class RecyclingRequestDto {
    private ItemType itemType;
    private Long count;
}
