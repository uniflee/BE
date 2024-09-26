package uniflee.backend.designer.dto;

import static lombok.AccessLevel.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Schema(description = "디자이너 정보에 대한 응답 데이터 객체")
public class DesignerInfoResponse {
	@Schema(description = "디자이너의 이름", example = "홍길동")
	private String name;
	@Schema(description = "디자이너의 프로필 이미지 URL", example = "profileImage/1234-abcd-5678-efgh.jpg")
	private String profileImageUrl;
	@Schema(description = "디자이너의 배경 이미지 URL", example = "backgroundImage/1234-abcd-5678-efgh.jpg")
	private String backgroundImageUrl;
}
