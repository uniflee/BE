package uniflee.backend.designer.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniflee.backend.designer.repository.DesignerRepository;

@Service
@RequiredArgsConstructor
public class DesignerService {
	private final DesignerRepository designerRepository;
}
