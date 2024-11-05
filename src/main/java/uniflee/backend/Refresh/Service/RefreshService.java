package uniflee.backend.Refresh.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniflee.backend.Refresh.Dto.TokenDto;
import uniflee.backend.Refresh.Repository.RefreshRepository;
import uniflee.backend.Refresh.domain.RefreshToken;
import uniflee.backend.Security.JwtProvider;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenDto refreshAccessToken(String refreshToken) {
        if (refreshToken == null)
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND_ERROR);
        String username = jwtProvider.getUser(refreshToken);
        if (!jwtProvider.isRefreshToken(refreshToken))
            throw new CustomException(ErrorCode.TOKEN_CATEGORY_NOT_MATCHED_ERROR);
        if (!jwtProvider.validateToken(refreshToken))
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED_ERROR);
        if (!refreshRepository.existsByRefreshTokenAndUsername(refreshToken, username)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_DB_NOT_FOUND_ERROR);
        }

        refreshToken = jwtProvider.createRefreshToken(username);
        addRefreshToken(username, refreshToken);
        return TokenDto.builder()
                .accessToken(jwtProvider.createAccessToken(username))
                .refreshToken(jwtProvider.createRefreshToken(username))
                .build();
    }

    @Transactional
    public void addRefreshToken(String username, String refreshToken) {
        refreshRepository.deleteByUsername(username);
        refreshRepository.save(RefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build());
    }
}
