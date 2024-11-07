package uniflee.backend.aws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uniflee.backend.aws.dto.PreSignedUrlResponse;
import uniflee.backend.aws.service.AwsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aws")
@Tag(name = "AWS", description = "AWS S3 이미지 업로드 및 다운로드")
public class AwsController {
	private final AwsService s3ImageService;

	@GetMapping("/presigned-url")
	@Operation(summary = "S3 이미지 업로드 URL 생성", description = "해당 api 요청시 s3에 이미지를 업로드할 수 있는 URL을 반환합니다.")
	public ResponseEntity<PreSignedUrlResponse> generatePreSignedUploadUrl(
		@Schema(description = "이미지 타입", example = "profile", allowableValues = {"profile", "background",
			"product"}) @RequestParam("type") String type) {
		return ResponseEntity.ok().body(s3ImageService.generatePreSignedUploadUrl(type));
	}
}
