package uniflee.backend.aws.service;

import static uniflee.backend.global.exception.ErrorCode.*;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.aws.dto.PreSignedUrlResponse;
import uniflee.backend.global.exception.CustomException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsService {
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// preSigned url 만료 기간 설정
	private static Date getExpiration() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60; // 1시간으로 설정하기
		expiration.setTime(expTimeMillis);
		return expiration;
	}

	// preSigned url 요청 설정 객체 생성
	private GeneratePresignedUrlRequest getPutGeneratePresignedUrlRequest(String fileName, Date expiration) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest
			= new GeneratePresignedUrlRequest(bucket, fileName)
			.withMethod(HttpMethod.PUT)
			.withKey(fileName)
			.withExpiration(expiration);
		generatePresignedUrlRequest.addRequestParameter(
			Headers.S3_CANNED_ACL,
			CannedAccessControlList.PublicRead.toString());
		return generatePresignedUrlRequest;
	}

	// 실제 preSigned url 생성
	public PreSignedUrlResponse generatePreSignedUploadUrl(String type) {
		String imagePath = "";
		if(type.equals("product")){
			imagePath = "productImage/";
		}else if(type.equals("shopProfile")){
			imagePath = "shopProfileImage/";
		}else if(type.equals("shopBackground")){
			imagePath = "shopBackgroundImage/";
		}else if(StringUtils.hasText(type)){
			throw new CustomException(INPUT_INVALID_VALUE_ERROR);
		}
		String filePath = imagePath + createFileName();
		GeneratePresignedUrlRequest request = getPutGeneratePresignedUrlRequest(filePath, getExpiration());
		String preSignedUrl = amazonS3.generatePresignedUrl(request).toString();
		return new PreSignedUrlResponse(preSignedUrl, filePath);
	}

	private void checkFilename(String originalFilename) {
		if(!amazonS3.doesObjectExist(bucket, originalFilename)) {
			log.warn("해당 파일이 존재하지 않습니다.");
			throw new CustomException(AWS_S3_IMAGE_NOT_FOUND_ERROR);
		}
	}

	private String createFileName() {
		return UUID.randomUUID().toString();
	}
}
