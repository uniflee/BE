package uniflee.backend.item.service;

import static uniflee.backend.global.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import uniflee.backend.designer.domain.Designer;
import uniflee.backend.designer.service.DesignerService;
import uniflee.backend.global.exception.CustomException;
import uniflee.backend.item.domain.Item;
import uniflee.backend.item.dto.ItemRequestDto;
import uniflee.backend.item.dto.ItemUpdateRequest;
import uniflee.backend.item.dto.OwnItemResponse;
import uniflee.backend.item.repository.ItemRepository;
import uniflee.backend.itemDescription.domain.ItemDescription;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final DesignerService designerService;

	@Transactional
	public void createItem(ItemRequestDto request) {
		Designer designer = designerService.getDesigner();
		Item item = Item.builder()
			.designer(designer)
			.name(request.getName())
			.price(request.getPrice())
			.featuredImageUrl(request.getFeaturedImageUrl())
			.build();

		List<ItemDescription> descriptionList = request.getDescriptions().stream().map(
			description ->
				ItemDescription.builder()
					.description(description.getDescription())
					.imageUrl(description.getImageUrl())
					.item(item)
					.build()
		).toList();
		item.connectItemDetails(descriptionList);
		itemRepository.save(item);
	}

	@Transactional
	public void deleteItem(List<Long> productIds) {
		Designer designer = designerService.getDesigner();
		productIds.forEach(
			id -> {
				Item targetItem = itemRepository.findById(id).orElseThrow(
					() -> new CustomException(ITEM_NOT_FOUND_ERROR)
				);
				if(!targetItem.isItemOwner(designer)) {
					throw new CustomException(PRODUCT_ACCESS_DENIED_ERROR);
				}
				itemRepository.deleteById(id);
			}
		);
	}

	public List<OwnItemResponse> getOwnItems() {
		Designer designer = designerService.getDesigner();
		return itemRepository.findByDesigner(designer).stream().map(
			item -> OwnItemResponse.builder()
				.id(item.getId())
				.featuredImageUrl(item.getFeaturedImageUrl())
				.designerName(designer.getName())
				.name(item.getName())
				.price(item.getPrice())
				.build()
		).toList();
	}

	@Transactional
	public void updateItem(ItemUpdateRequest request, Long itemId) {
		Designer designer = designerService.getDesigner();
		Item item = itemRepository.findById(itemId).orElseThrow(
			() -> new CustomException(ITEM_NOT_FOUND_ERROR)
		);
		if(!item.isItemOwner(designer)) {
			throw new CustomException(PRODUCT_ACCESS_DENIED_ERROR);
		}
		item.updateItem(request);

		request.getDescriptions().stream().map(
			description ->
				ItemDescription.builder()
					.description(description.getDescription())
					.imageUrl(description.getImageUrl())
					.item(item)
					.build()
		).forEach(
			description -> {
				item.getItemDetails().add(description);
			}
		);
	}
}
