package uniflee.backend.designer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uniflee.backend.designer.dto.BackgroundImageUpdateRequest;
import uniflee.backend.designer.dto.DesignerInfoResponse;
import uniflee.backend.designer.dto.DesignerNameUpdateRequest;
import uniflee.backend.designer.dto.ProfileImageUpdateRequest;
import uniflee.backend.designer.service.DesignerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/designer")
@Tag(name = "DesignerController", description = "디자이너 정보 관리 API")
public class DesignerController {
	private final DesignerService designerService;

	@Operation(
			summary = "디자이너 정보를 조회합니다.",
			description = "디자이너의 이름, 프로필 이미지, 배경 이미지를 조회합니다."
	)
	@GetMapping
	public ResponseEntity<DesignerInfoResponse> getDesignerInfo() {
		return ResponseEntity.ok(designerService.getDesignerInfo());
	}

	@Operation(
			summary = "디자이너 정보를 수정합니다.",
			description = "디자이너의 이름을 수정합니다."
	)
	@PatchMapping("/name")
	public ResponseEntity<?> updateName(@RequestBody DesignerNameUpdateRequest request) {
		designerService.updateName(request.getName());
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "디자이너 정보를 수정합니다.",
			description = "디자이너의 프로필 이미지를 수정합니다."
	)
	@PatchMapping("/profileImage")
	public ResponseEntity<?> updateProfileImage(@RequestBody ProfileImageUpdateRequest request) {
		designerService.updateProfileImage(request.getProfileImageUrl());
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "디자이너 정보를 수정합니다.",
			description = "디자이너의 배경 이미지를 수정합니다."
	)
	@PatchMapping("/backgroundImage")
	public ResponseEntity<?> updateBackgroundImage(@RequestBody BackgroundImageUpdateRequest request) {
		designerService.updateBackgroundImage(request.getBackgroundImage());
		return ResponseEntity.ok().build();
	}
}
