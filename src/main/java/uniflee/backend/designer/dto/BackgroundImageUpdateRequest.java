package uniflee.backend.designer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "디자이너의 배경 이미지 수정 요청 데이터 객체")
public class BackgroundImageUpdateRequest {
	@Schema(description = "디자이너의 배경 이미지 URL", example = "backgroundImage/1234-abcd-5678-efgh.jpg")
	private String backgroundImage;
}
