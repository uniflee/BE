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
    @Schema(description = "멤버십 환경 보호 영향", example = "{\n" +
            "  \"totalPoints\": 10,\n" +
            "  \"grade\": \"BRONZE\",\n" +
            "  \"gradeImpact\": {\n" +
            "    \"treesProtected\": \"5-10 그루\",\n" +
            "    \"energySaved\": \"100 kWh\",\n" +
            "    \"plasticPrevented\": \"10 kg\",\n" +
            "    \"co2Reduced\": \"100 kg\"\n" +
            "  }")
    GradeImpact gradeImpact;
    @Schema(description = "유저 이름", example = "김선미")
    String name;
}