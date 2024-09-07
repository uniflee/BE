package uniflee.backend.user.domain;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniflee.backend.global.domain.Address;
import uniflee.backend.global.domain.BaseEntity;
import uniflee.backend.orders.domain.Orders;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private Long point;
	private Grade grade;
	private String username;
	private String password;
	@Embedded
	private Address address;

	@OneToMany(mappedBy = "user")
	private List<Orders> orders;

	@Getter
	@AllArgsConstructor
	private enum Grade{
		BRONZE(0.01),
		SILVER(0.03),
		GOLD(0.06),
		PLATINUM(0.09),
		DIAMOND(0.15);
		private final double discountRate;
	}
}
