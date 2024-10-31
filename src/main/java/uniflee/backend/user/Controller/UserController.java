package uniflee.backend.user.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uniflee.backend.user.Dto.MembershipDto;
import uniflee.backend.user.Service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "유저 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "유저 멤버십 등급을 가져옵니다.",
            description = "멤버십 등급, 환경 보호 리포트, 총 누적 포인트를 가져옵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MembershipDto.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"totalPoints\": 10,\n" +
                                    "  \"grade\": \"BRONZE\",\n" +
                                    "  \"gradeImpact\": {\n" +
                                    "    \"treesProtected\": \"5-10 그루\",\n" +
                                    "    \"energySaved\": \"100 kWh\",\n" +
                                    "    \"plasticPrevented\": \"10 kg\",\n" +
                                    "    \"co2Reduced\": \"100 kg\"\n" +
                                    "  }\n" +
                                    "}")))
    })
    @GetMapping("/membership")
    public ResponseEntity<MembershipDto> userMembership(Authentication authentication) {
        MembershipDto membershipDto = userService.getMembership(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(membershipDto);
    }
}
