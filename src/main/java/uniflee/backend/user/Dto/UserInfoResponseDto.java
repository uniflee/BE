package uniflee.backend.user.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniflee.backend.user.domain.User;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Schema(description = "로그인 시 유저 정보 확인 DTO")
public class UserInfoResponseDto {
    @Schema(description = "유저ID", example = "1")
    private Long id;
    @Schema(description = "유저 이름", example = "김선미")
    private String name;
    @Schema(description = "유저 등급", example = "BRONZE")
    private User.Grade grade;
    @Schema(description = "유저 로그인 ID", example = "hariaus")
    private String username;
    @Schema(description = "누적 포인트", example = "10000")
    private Long totalPoints;
    @Schema(description = "보유 포인트", example = "10000")
    private Long currentPoints;
}
