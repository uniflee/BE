package uniflee.backend.Recycling.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniflee.backend.Recycling.Dto.RecyclingResponseDto;
import uniflee.backend.Recycling.Repository.RecyclingRepository;
import uniflee.backend.Recycling.domain.ItemType;
import uniflee.backend.Recycling.domain.Recycling;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;
import uniflee.backend.user.Repository.UserRepository;
import uniflee.backend.user.Service.UserService;
import uniflee.backend.user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecyclingService {

    private final RecyclingRepository recyclingRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public void addRecycling(String username, ItemType itemType, Long count) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

        userService.updatePoints(user, itemType.getPoints() * count, 0L);

        recyclingRepository.save(Recycling.builder()
                .itemType(itemType)
                .count(count)
                .user(user)
                .build());
    }

    public List<RecyclingResponseDto> getRecycling(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR));

        return user.getRecycling().stream().map(recycling ->
                RecyclingResponseDto.builder()
                        .itemType(recycling.getItemType())
                        .count(recycling.getCount())
                        .point(recycling.getItemType().getPoints() * recycling.getCount())
                        .build()
        ).toList();
    }
}
