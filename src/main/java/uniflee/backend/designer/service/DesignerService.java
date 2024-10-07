package uniflee.backend.designer.service;

import static uniflee.backend.global.exception.ErrorCode.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uniflee.backend.designer.domain.CustomDesignerDetails;
import uniflee.backend.designer.domain.Designer;
import uniflee.backend.designer.dto.DesignerInfoResponse;
import uniflee.backend.designer.dto.SignRequest;
import uniflee.backend.designer.repository.DesignerRepository;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.global.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesignerService implements UserDetailsService {
	private final DesignerRepository designerRepository;
	private final PasswordEncoder passwordEncoder;

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
	public DesignerInfoResponse getDesignerInfo() {
		Designer designer = getDesigner();
		return DesignerInfoResponse.builder()
			.name(designer.getName())
			.profileImageUrl(designer.getProfileImageUrl())
			.backgroundImageUrl(designer.getBackgroundImageUrl())
			.build();
	}

	public Designer getDesigner() {
		String designerName = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("designerName: {}", designerName);
		return designerRepository.findByUsername(designerName).orElseThrow(
			() -> new CustomException(NOT_FOUND_USER_ERROR)
		);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Designer designer = designerRepository.findByUsername(username).orElseThrow(
				() -> new CustomException(NOT_FOUND_USER_ERROR));

		return new CustomDesignerDetails(designer);
	}

	@Transactional
	public void addDesigner(SignRequest signRequest) {
		if (designerRepository.existsByUsername(signRequest.getUsername())) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_USER_ERROR);
		}

		Designer designer = Designer.builder()
				.name(signRequest.getName())
				.username(signRequest.getUsername())
				.password(passwordEncoder.encode(signRequest.getPassword()))
				.build();

		designerRepository.save(designer);
	}
}
