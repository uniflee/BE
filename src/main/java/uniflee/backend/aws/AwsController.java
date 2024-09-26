package uniflee.backend.aws;

import java.io.IOException;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aws")
@Tag(name = "AWS", description = "AWS S3 이미지 업로드 및 다운로드")
public class AwsController {
	private final AwsService s3ImageService;

	@GetMapping
	@Operation(summary = "S3 이미지 다운로드", description = "해당 api 요청시 s3에 저장된 이미지를 다운로드 합니다.")
	public ResponseEntity<UrlResource> downloadImageInS3(
		@PathParam("imageName") @Schema(description = "s3 이미지 위치", example = "profileImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png") String name) {
		return s3ImageService.downloadImage(name);
	}

	@PostMapping(consumes = {"multipart/form-data"})
	@Operation(summary = "S3 이미지 업로드", description = "해당 api 요청시 s3에 이미지를 업로드 후에 이미지의 url을 반환합니다.")

	public ResponseEntity<String> uploadImageToS3(@RequestPart MultipartFile image,
		@Schema(description = "이미지 타입", example = "profile", allowableValues = {"profile", "background",
			"product"}) @RequestParam("type") String type) throws
		IOException {

		return ResponseEntity.ok().body(s3ImageService.saveImage(image, type));
	}

	@DeleteMapping("/{name}")
	@Operation(summary = "S3 이미지 삭제", description = "해당 api 요청시 s3에 저장된 이미지를 삭제합니다.")
	public void deleteImageInS3(
		@PathVariable("name") @Schema(description = "s3 이미지 위치", example = "profileImage/a3821089-02ef-4a3c-946d-6e3bf3456658.png") String name) {
		s3ImageService.deleteImage(name);
	}
}
