package uniflee.backend.itemDescription.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniflee.backend.itemDescription.repository.ItemDescriptionRepository;

@Service
@RequiredArgsConstructor
public class ItemDescriptionService {
	private final ItemDescriptionRepository itemDetailRepository;
}
