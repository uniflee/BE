package uniflee.backend.designer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uniflee.backend.designer.dto.DesignerInfoResponse;
import uniflee.backend.designer.dto.DesignerInfoUpdateDto;
import uniflee.backend.designer.service.DesignerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/designer")
public class DesignerController {
	private final DesignerService designerService;

	@GetMapping
	public ResponseEntity<DesignerInfoResponse> getDesignerInfo() {
		return ResponseEntity.ok(designerService.getDesignerInfo());
	}

	@PatchMapping("/name")
	public ResponseEntity<String> updateName(@RequestBody DesignerInfoUpdateDto request) {
		designerService.updateName(request.getName());
		return ResponseEntity.ok("name");
	}

	@PatchMapping("/profileImage")
	public ResponseEntity<String> updateProfileImage(@RequestBody DesignerInfoUpdateDto request) {
		designerService.updateProfileImage(request.getProfileImage());
		return ResponseEntity.ok("profileImage");
	}

	@PatchMapping("/backgroundImage")
	public ResponseEntity<String> updateBackgroundImage(@RequestBody DesignerInfoUpdateDto request) {
		designerService.updateBackgroundImage(request.getBackgroundImage());
		return ResponseEntity.ok("backgroundImage");
	}
}
