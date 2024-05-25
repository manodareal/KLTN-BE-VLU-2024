package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.domain.service.CartService;
import com.example.usermanagement.domain.service.CustomerService;
import com.example.usermanagement.dto.user.input.CartItemInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;
    @PostMapping("/cart")
    public ResponseEntity<Cart> createOrAddToPendingCart(@RequestParam String customerId, @RequestBody CartItemInput cartItemInput) {
        Cart cart = cartService.createOrAddToPendingCart(customerId, cartItemInput);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeCart(@RequestParam String customerId) {
        cartService.completeCart(customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeProductFromCart(@RequestParam String customerId, @RequestParam String productId) {
        cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
