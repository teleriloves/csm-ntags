package com.cs.money.nametagseeker;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ItemRetrievalService {

	@Autowired ItemRetrievalClient itemClient;
	
	private Predicate<Item> hasNametag = item -> Objects.nonNull(item.getN());
	private Predicate<Item> isAvailable = item -> CollectionUtils.isEmpty(item.getUi());
	private Predicate<Item> isAffordable = item -> item.getP() < Double.valueOf(2);
	private Predicate<Item> filterItems = 	isAvailable.and(isAffordable).and(hasNametag);
	
	private Comparator<Item> priceComparator = Comparator.comparing(Item::getP);

	public List<Item> seek() {
		ResponseEntity<Item[]> listItems = itemClient.getItems();
		List<Item> safeItems = Optional.ofNullable(listItems).map(ResponseEntity::getBody)
				.map(Arrays::asList).orElse(Collections.emptyList());
		return safeItems.parallelStream().filter(filterItems)
			.sorted(priceComparator).collect(Collectors.toList());
	}

	public List<String> seekFormatted() {
		List<Item> items = seek();
		return items.stream().map(Item::toString).collect(Collectors.toList());
	}
}
