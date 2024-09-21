package uniflee.backend.item.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniflee.backend.item.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
}
