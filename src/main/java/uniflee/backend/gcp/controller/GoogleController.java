package uniflee.backend.gcp.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.gcp.service.GoogleService;

@Slf4j
@RestController
@RequestMapping("/api/google")
@RequiredArgsConstructor
public class GoogleController {
	private final GoogleService googleService;

	@Value("${google.spreadsheet.id}")
	private String SPREAD_SHEET_ID;
	private static final String RANGE = "A1";

	@PostMapping("/write")
	public ResponseEntity<String> writeToSheet(@RequestParam String word) {
		log.info("google sheet id: {}", SPREAD_SHEET_ID);
		try {
			// 3. 데이터를 스프레드시트에 쓰기 위해 전달되는 형식
			// 행과 열에 데이터를 매핑하기 위함
			List<List<Object>> values = List.of(Collections.singletonList(word));

			// 4. 서비스 로직 호출
			googleService.writeToSheet(SPREAD_SHEET_ID, RANGE, values);
			return ResponseEntity.ok("Data written successfully to the spreadsheet: " + word);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Failed to write data: " + e.getMessage());
		}
	}
}
