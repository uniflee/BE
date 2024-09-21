package uniflee.backend.designer.domain;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uniflee.backend.global.domain.BaseEntity;
import uniflee.backend.item.domain.Item;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Designer extends BaseEntity {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private String profileImageUrl;
	private String backgroundImageUrl;
	private String username;
	private String password;

	@OneToMany(mappedBy = "designer")
	private List<Item> items = new ArrayList<>();

	public void updateName(String name) {
		this.name = name;
	}
	public void updateProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public void updateBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}
}
