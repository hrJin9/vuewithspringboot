package org.dallae.gallery.backend.controller;

import lombok.RequiredArgsConstructor;
import org.dallae.gallery.backend.entity.Item;
import org.dallae.gallery.backend.repository.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/api/items")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemRepository.findAll();

        return ResponseEntity.ok(items);
    }
}
