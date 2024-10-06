package uniflee.backend.user.Dto;

import lombok.Builder;
import lombok.Getter;
import uniflee.backend.user.domain.GradeImpact;
import uniflee.backend.user.domain.User;

@Getter
@Builder
public class MembershipDto {

    Long totalPoints;
    User.Grade grade;
    GradeImpact gradeImpact;

}
