package uniflee.backend.designer.dto;

import static lombok.AccessLevel.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class DesignerInfoResponse {
	private String name;
	private String profileImageUrl;
	private String backgroundImageUrl;
}
