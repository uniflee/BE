package uniflee.backend.designer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "디자이너의 이름 수정 요청 데이터 객체")
public class DesignerNameUpdateRequest {
	@Schema(description = "디자이너의 이름", example = "홍길동")
	private String name;
}
