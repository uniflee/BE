package uniflee.backend.aws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PreSignedUrlResponse {
	@Schema(description = "S3 업로드를 요청하는 presigned 된 url", example = "http://localhost:9000/unique-storage-service/61b960f8-e4ae-4c8d-8285-2c467c7477f2.jpeg?x-amz-acl=public-read&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241107T044344Z&X-Amz-SignedHeaders=host&X-Amz-Expires=59&X-Amz-Credential=g9p5C9cseaQOdd47G1tR%2F20241107%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=3cc04ec63bd6cc61faf8fa1f3d10fcb597cf4d0cb7bd37eb2a7ba7a524eb54c6")
	private String preSignedUrl;
	@Schema(description = "업로드 된 파일의 경로", example = "/profile/61b960f8-e4ae-4c8d-8285-2c467c7477f2.jpeg")
	private String resourceUrl;
}
