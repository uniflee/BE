package uniflee.backend.gcp.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderInfo {
	private String id;
	private Long ItemId;
	private LocalDateTime orderDate;
	private Long count;
}
