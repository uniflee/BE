package uniflee.backend.user.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniflee.backend.user.Dto.SignDto;
import uniflee.backend.user.Service.UserService;
import uniflee.backend.user.domain.User;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> userSave(@RequestBody SignDto signDto) {
        User user = userService.addUser(signDto);
        return ResponseEntity.ok().body(user);
    }
}
