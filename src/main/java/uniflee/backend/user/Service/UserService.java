package uniflee.backend.user.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniflee.backend.user.Dto.MembershipDto;
import uniflee.backend.user.domain.CustomUserDetails;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;
import uniflee.backend.user.Dto.SignDto;
import uniflee.backend.user.Repository.UserRepository;
import uniflee.backend.user.domain.GradeImpact;
import uniflee.backend.user.domain.User;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User addUser(SignDto signDto) {
        if (userRepository.existsByUsername(signDto.getUsername())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER_ERROR);
        }

        User user = User.builder()
                .name(signDto.getName())
                .currentPoints(0L)
                .totalPoints(0L)
                .grade(User.Grade.BRONZE)
                .username(signDto.getUsername())
                .password(passwordEncoder.encode(signDto.getPassword()))
                .address(signDto.getAddress())
                .build();

        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

        return new CustomUserDetails(user);
    }

    public MembershipDto getMembership(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

        return MembershipDto.builder()
                .totalPoints(user.getTotalPoints())
                .grade(user.getGrade())
                .gradeImpact(GradeImpact.valueOf(user.getGrade().name()))
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
}
