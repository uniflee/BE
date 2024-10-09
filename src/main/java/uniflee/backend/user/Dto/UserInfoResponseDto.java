package uniflee.backend.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uniflee.backend.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponseDto {
    private Long id;
    private String name;
    private User.Grade grade;
    private String username;
}
