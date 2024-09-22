package uniflee.backend.item.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ItemUpdateRequest {
	private String featuredImageUrl;
	private String name;
	private Long price;
	private List<ItemDescription> descriptions;
}
