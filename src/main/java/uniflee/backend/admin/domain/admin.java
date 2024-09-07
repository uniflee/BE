package uniflee.backend.admin.domain;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class admin {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String username;
	private String password;
}
