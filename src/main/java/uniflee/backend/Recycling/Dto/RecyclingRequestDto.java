package uniflee.backend.Recycling.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import uniflee.backend.Recycling.domain.ItemType;

@Getter
@Setter
public class RecyclingRequestDto {
    @Schema(description = "재활용품의 종류", example = "PP")
    private ItemType itemType;
    @Schema(description = "디자이너의 프로필 이미지 URL", example = "profileImage/1234-abcd-5678-efgh.jpg")
    private Long count;
}

