package uniflee.backend.user.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniflee.backend.user.domain.CustomUserDetails;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;
import uniflee.backend.user.Dto.SignDto;
import uniflee.backend.user.Repository.UserRepository;
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
                .point(0L)
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
}
