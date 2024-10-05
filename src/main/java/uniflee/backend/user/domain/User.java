package uniflee.backend.user.domain;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import uniflee.backend.Recycling.domain.Recycling;
import uniflee.backend.global.domain.Address;
import uniflee.backend.global.domain.BaseEntity;
import uniflee.backend.orders.domain.Orders;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private Long totalPoints; // 누적 포인트
	private Long currentPoints; // 현재 가지고 있는 포인트
	private Grade grade;
	private String username;
	private String password;
	@Embedded
	private Address address;

	@OneToMany(mappedBy = "user")
	private List<Orders> orders = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Recycling> recycling = new ArrayList<>();

	@Getter
	@AllArgsConstructor
	public enum Grade{
		BRONZE(0.01),
		SILVER(0.03),
		GOLD(0.06),
		PLATINUM(0.09),
		DIAMOND(0.15);
		private final double discountRate;
	}

	public void updateMembership(Grade grade, Long currentPoints, Long totalPoints) {
		this.grade = grade;
		this.currentPoints = currentPoints;
		this.totalPoints = totalPoints;
	}
}
