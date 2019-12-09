package com.cs.money.nametagseeker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items")
public class ItemController {

	@Autowired private ItemRetrievalService itemService;
	
	@GetMapping(produces = "application/json")
    public @ResponseBody List<Item> getItems() {
        return itemService.seek();
    }
	
}
