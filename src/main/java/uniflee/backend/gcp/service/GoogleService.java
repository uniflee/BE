package uniflee.backend.gcp.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.gcp.dto.OrderInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService {
	private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String CREDENTIALS_FILE_PATH = "/google/google.json";
	private Sheets sheetsService;

	@Value("${google.spreadsheet.id}")
	private String SPREAD_SHEET_ID;
	private static final String RANGE = "sheet1!A1:C1";
	private Sheets getSheetsService() throws IOException, GeneralSecurityException {
		if(sheetsService == null){
			GoogleCredentials credential = GoogleCredentials.fromStream(
					new ClassPathResource(CREDENTIALS_FILE_PATH).getInputStream())
				.createScoped("https://www.googleapis.com/auth/spreadsheets");
			sheetsService = new Sheets.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credential)).setApplicationName("uniflee").build();
		}
		return sheetsService;
	}

	public void writeToSheet(OrderInfo request) {
		try {
			List<List<Object>> values = List.of(
				List.of(request.getId(), "미수령", request.getOrderDate().toString(), request.getItemId().toString(), request.getCount())
			);
			Sheets service = getSheetsService();
			ValueRange body = new ValueRange().setValues(values);
			AppendValuesResponse result = service.spreadsheets()
				.values()
				.append(SPREAD_SHEET_ID, RANGE, body)
				.setValueInputOption("USER_ENTERED")
				.setIncludeValuesInResponse(true)
				.execute();
		} catch (Exception e) {
			log.error("Failed to write data to the spreadsheet", e);
			throw new RuntimeException("Failed to write data to the spreadsheet: " + e.getMessage(), e);
		}
	}
}
