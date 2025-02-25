package org.dallae.gallery.backend.controller;

import lombok.RequiredArgsConstructor;
import org.dallae.gallery.backend.entity.Cart;
import org.dallae.gallery.backend.entity.Item;
import org.dallae.gallery.backend.repository.CartRepository;
import org.dallae.gallery.backend.repository.ItemRepository;
import org.dallae.gallery.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.lang.model.type.NullType;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final JwtService jwtService;

    @GetMapping("/api/cart/items")
    public ResponseEntity<List<Item>> getCartItems(
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Long memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Long> itemIds = carts.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity<Void> pushCartItem(
            @PathVariable("itemId") Long itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Long memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        // 해당하는 카트가 없으면 새로운 카트를 추가한다.
        if(cart == null) {
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);

            cartRepository.save(newCart);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable("itemId") Long itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Long memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        cartRepository.delete(cart);
        return ResponseEntity.ok().build();
    }
}
