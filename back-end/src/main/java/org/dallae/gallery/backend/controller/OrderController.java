package org.dallae.gallery.backend.controller;

import lombok.RequiredArgsConstructor;
import org.dallae.gallery.backend.dto.OrderDto;
import org.dallae.gallery.backend.entity.Cart;
import org.dallae.gallery.backend.entity.Order;
import org.dallae.gallery.backend.repository.OrderRepository;
import org.dallae.gallery.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.lang.model.type.NullType;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final JwtService jwtService;
    private final OrderRepository orderRepository;

    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {
        List<Order> orders = orderRepository.findAll();

        return ResponseEntity.ok(orders);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<Void> pushOrder(
            @RequestBody OrderDto orderDto,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Order newOrder = new Order();
        newOrder.setMemberId(jwtService.getId(token));
        newOrder.setName(orderDto.getName());
        newOrder.setAddress(orderDto.getAddress());
        newOrder.setPayment(orderDto.getPayment());
        newOrder.setCardNumber(orderDto.getCardNumber());
        newOrder.setItems(orderDto.getItems());

        orderRepository.save(newOrder);

        return ResponseEntity.ok().build();
    }
}
