package uniflee.backend.user.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uniflee.backend.user.Dto.MembershipDto;
import uniflee.backend.user.Dto.SignDto;
import uniflee.backend.user.Service.UserService;
import uniflee.backend.user.domain.User;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> userSave(@RequestBody SignDto signDto) {
        User user = userService.addUser(signDto);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/membership")
    public ResponseEntity<MembershipDto> userMembership(Authentication authentication) {
        MembershipDto membershipDto = userService.getMembership(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(membershipDto);
    }
}
