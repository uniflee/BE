package uniflee.backend.item.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uniflee.backend.designer.domain.Designer;
import uniflee.backend.global.domain.BaseEntity;
import uniflee.backend.orders.domain.Orders;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private Long price;
	private String image;
	private String description;

	@OneToMany(mappedBy = "item")
	private List<Orders> orders;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "designer_id")
	private Designer designer;
}
