package uniflee.backend.Refresh.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {
    String accessToken;
    String refreshToken;
}
