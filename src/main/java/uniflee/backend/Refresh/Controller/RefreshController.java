package uniflee.backend.Refresh.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniflee.backend.Refresh.Dto.TokenDto;
import uniflee.backend.Refresh.Service.RefreshService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh")
public class RefreshController {

    private final RefreshService refreshService;

    @PostMapping
    public ResponseEntity<TokenDto> refreshAccessToken(@RequestHeader("Refresh-token") String refreshToken) {
        TokenDto tokenDto = refreshService.refreshAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }
}
