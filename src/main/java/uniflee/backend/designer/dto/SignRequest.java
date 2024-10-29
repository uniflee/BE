package uniflee.backend.designer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "디자이너 회원가입 요청 데이터 객체")
public class SignRequest {
    @Schema(description = "디자이너의 본명", example = "김선미")
    private String name;
    @Schema(description = "디자이너의 아이디", example = "hariaus")
    private String username;
    @Schema(description = "디자이너의 비밀번호", example = "1234")
    private String password;
}
