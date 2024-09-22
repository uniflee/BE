package uniflee.backend.aws;

import static uniflee.backend.global.exception.ErrorCode.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.global.exception.CustomException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsService {
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String saveImage(MultipartFile multipartFile, String type) throws IOException {
		if(multipartFile == null || type == null) {
			throw new CustomException(INPUT_INVALID_VALUE_ERROR);
		}

		String originalFilename = multipartFile.getOriginalFilename();
		String extend = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);

		// 허용되는 확장자 리스트
		List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png");

		if(!allowedExtentionList.contains(extend)) {
			throw new CustomException(AWS_S3_IMAGE_EXTEND_ERROR);
		}

		// TODO : 이미지가 추가되면 path 추가
		String imagePath = "";
		if(type.equals("product")){
			imagePath = "productImage/";
		}else if(type.equals("shopProfile")){
			imagePath = "shopProfileImage/";
		}else if(type.equals("shopBackground")){
			imagePath = "shopBackgroundImage/";
		}

		String fileName = imagePath + createFileName(originalFilename).concat("." + extend);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

		amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);

		String resourceURL = amazonS3.getUrl(bucket, fileName).toString();
		return resourceURL.substring(resourceURL.lastIndexOf("/"));
	}

	public ResponseEntity<UrlResource> downloadImage(String originalFilename) {
		checkFilename(originalFilename);

		UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));
		String contentDisposition = "attachment; filename=\"" +  originalFilename + "\"";

		// header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
			.body(urlResource);
	}

	public void deleteImage(String originalFilename) {
		try {
			checkFilename(originalFilename);
			amazonS3.deleteObject(bucket, originalFilename);
		} catch (AmazonS3Exception e) {
			log.error("Failed to delete " + originalFilename + ": " + e.getMessage());
			e.printStackTrace();
		} catch (AmazonServiceException e) {
			log.error("AWS error occurred: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Unexpected error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void checkFilename(String originalFilename) {
		if(!amazonS3.doesObjectExist(bucket, originalFilename)) {
			log.warn("해당 파일이 존재하지 않습니다.");
			throw new CustomException(AWS_S3_IMAGE_NOT_FOUND_ERROR);
		}
	}

	private String createFileName(String originalFilename) {
		return UUID.randomUUID().toString();
	}
}
