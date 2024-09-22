package uniflee.backend.aws;

import java.io.IOException;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aws")
public class AwsController {
	private final AwsService s3ImageService;

	@GetMapping
	public ResponseEntity<UrlResource> downloadImageInS3(@PathParam("imageName") String name) {
		return s3ImageService.downloadImage(name);
	}

	@PostMapping
	public ResponseEntity<String> uploadImageToS3(@RequestPart MultipartFile image, @RequestParam("type") String type) throws
		IOException {
		return ResponseEntity.ok().body(s3ImageService.saveImage(image, type));
	}

	@DeleteMapping
	public void deleteImageInS3(@PathParam("name") String name) {
		s3ImageService.deleteImage(name);
	}
}
