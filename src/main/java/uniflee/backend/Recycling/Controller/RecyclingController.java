package uniflee.backend.Recycling.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uniflee.backend.Recycling.Dto.RecyclingRequestDto;
import uniflee.backend.Recycling.Dto.RecyclingResponseDto;
import uniflee.backend.Recycling.Service.RecyclingService;
import uniflee.backend.Recycling.domain.ItemType;

import java.util.List;

@RestController
@RequestMapping("/api/recycling")
@RequiredArgsConstructor
public class RecyclingController {

    private final RecyclingService recyclingService;

    @PostMapping
    public ResponseEntity<String> addRecycling(Authentication authentication,
                                               @RequestBody RecyclingRequestDto recyclingDto) {
        recyclingService.addRecycling(authentication.getName(), recyclingDto.getItemType(), recyclingDto.getCount());
        return ResponseEntity.ok("포인트 적립");
    }

    @GetMapping
    public ResponseEntity<List<RecyclingResponseDto>> getRecycling(Authentication authentication) {
        List<RecyclingResponseDto> responseDtoList = recyclingService.getRecycling(authentication.getName());
        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/guide")
    public ResponseEntity<String> getRecyclingGuide(@RequestParam String itemType) {
        return ResponseEntity.ok(ItemType.valueOf(itemType).getDisposalInstructions());
    }
}
