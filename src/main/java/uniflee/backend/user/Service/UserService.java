package uniflee.backend.user.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import uniflee.backend.user.Dto.MembershipDto;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;
import uniflee.backend.user.Dto.OAuth2UserInfo;
import uniflee.backend.user.Dto.PrincipalUserDetails;
import uniflee.backend.user.Dto.UserInfoResponseDto;
import uniflee.backend.user.Repository.UserRepository;
import uniflee.backend.user.domain.GradeImpact;
import uniflee.backend.user.domain.User;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public MembershipDto getMembership(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

        return MembershipDto.builder()
                .totalPoints(user.getTotalPoints())
                .grade(user.getGrade())
                .gradeImpact(GradeImpact.valueOf(user.getGrade().name()))
                .name(user.getName())
                .build();
    }

    public UserInfoResponseDto getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

        return UserInfoResponseDto.builder()
                .id(user.getId())
                .currentPoints(user.getCurrentPoints())
                .totalPoints(user.getTotalPoints())
                .grade(user.getGrade())
                .username(username)
                .name(user.getName())
                .grade(user.getGrade())
                .build();
    }

    private User.Grade determineGrade(Long totalPoints) {
        if (totalPoints >= 20000) {
            return User.Grade.DIAMOND;
        } else if (totalPoints >= 10000) {
            return User.Grade.PLATINUM;
        } else if (totalPoints >= 5000) {
            return User.Grade.GOLD;
        } else if (totalPoints >= 1000) {
            return User.Grade.SILVER;
        } else {
            return User.Grade.BRONZE;
        }
    }

    @Transactional
    public void updatePoints(User user, Long pointsToEarn, Long pointsToSpend) {
        Long totalPoints = user.getTotalPoints() + pointsToEarn;

        Long currentPoint = user.getCurrentPoints() - pointsToSpend + pointsToEarn;
        if (currentPoint <= 0)
            throw new CustomException(ErrorCode.INSUFFICIENT_USER_POINTS);

        user.updateMembership(determineGrade(totalPoints), currentPoint, totalPoints);
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> oAuth2UserAttributes = oauth2User.getAttributes();

        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo.KookMinUserInfo(oAuth2UserAttributes);

        User user = userRepository.findByUsername(oAuth2UserInfo.getUsername()).orElseGet(
                () -> saveUser(oAuth2UserInfo));

        return new PrincipalUserDetails(user, oAuth2UserAttributes);
    }

    private User saveUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .username(oAuth2UserInfo.getUsername())
                .name(oAuth2UserInfo.getName())
                .grade(User.Grade.BRONZE)
                .totalPoints(0L)
                .currentPoints(0L)
                .build();

        userRepository.save(user);
        return user;
    }
}
