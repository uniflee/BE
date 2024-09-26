package uniflee.backend.item.dto;

import static lombok.AccessLevel.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OwnItemResponse {
	private Long id;
	private String featuredImageUrl;
	private String designerName;
	private String name;
	private Long price;
}
