package uniflee.backend.global.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Address {
	private String address;
	private String detailAddress;
	private String zipCode;
}
