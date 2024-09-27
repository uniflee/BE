package uniflee.backend.gcp.controller;

import static java.time.LocalDateTime.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.gcp.dto.OrderInfo;
import uniflee.backend.gcp.service.GoogleService;

@Slf4j
@RestController
@RequestMapping("/api/google")
@RequiredArgsConstructor
public class GoogleController {
	private final GoogleService googleService;

	@Value("${google.spreadsheet.id}")
	private String SPREAD_SHEET_ID;
	private static final String RANGE = "sheet1!A1:C1";

	@PostMapping("/write")
	public ResponseEntity<?> writeToSheet() {
		try {
			OrderInfo orderInfo = OrderInfo.builder()
				.id(1L)
				.ItemId(0L)
				.orderDate(now())
				.build();
			googleService.writeToSheet(SPREAD_SHEET_ID, RANGE, orderInfo);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Failed to write data: " + e.getMessage());
		}
	}
}
