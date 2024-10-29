package uniflee.backend.user.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import uniflee.backend.user.domain.GradeImpact;
import uniflee.backend.user.domain.User;

@Getter
@Builder
@Schema(description = "Membership 조회 DTO")
public class MembershipDto {
    @Schema(description = "총 적립 포인트", example = "10000")
    Long totalPoints;
    @Schema(description = "유저 멤버십 등급", example = "BRONZE")
    User.Grade grade;
    GradeImpact gradeImpact;

}
