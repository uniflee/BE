package uniflee.backend.designer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "디자이너의 프로필 이미지 수정 요청 데이터 객체")
public class ProfileImageUpdateRequest {
	@Schema(description = "디자이너의 프로필 이미지 URL", example = "profileImage/1234-abcd-5678-efgh.jpg")
	private String profileImageUrl;
}
