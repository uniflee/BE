package uniflee.backend.itemDescription.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniflee.backend.item.domain.Item;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ItemDescription {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String imageUrl;
	private String description;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
}