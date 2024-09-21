package uniflee.backend.designer.service;

import static uniflee.backend.global.exception.ErrorCode.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.designer.domain.Designer;
import uniflee.backend.designer.repository.DesignerRepository;
import uniflee.backend.global.exception.CustomException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesignerService {
	private final DesignerRepository designerRepository;

	@Transactional
	public void updateName(String name) {
		checkInput(name);
		Designer designer = getDesigner();
		designer.updateName(name);
	}

	@Transactional
	public void updateProfileImage(String profileImageUrl) {
		checkInput(profileImageUrl);
		Designer designer = getDesigner();
		designer.updateProfileImageUrl(profileImageUrl);
	}

	@Transactional
	public void updateBackgroundImage(String backgroundImageUrl) {
		checkInput(backgroundImageUrl);
		Designer designer = getDesigner();
		designer.updateBackgroundImageUrl(backgroundImageUrl);
	}

	private void checkInput(String input) {
		if(!StringUtils.hasText(input)){
			// TODO : merge하고 수정
			// throw new CustomException(INVALID_INPUT_VALUE_ERROR);
		}
	}

	private Designer getDesigner() {
		String designerName = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("designerName: {}", designerName);
		return designerRepository.findByUsername(designerName).orElseThrow(
			() -> new CustomException(NOT_FOUND_USER_ERROR)
		);

	}
}
