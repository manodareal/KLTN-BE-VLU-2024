package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.service.CartService;
import com.example.usermanagement.dto.CartDTO;
import com.example.usermanagement.dto.user.input.CartItemInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CartDTO>> getCartsByCustomerId(@PathVariable String customerId) {
        List<CartDTO> cartDTOs = cartService.getCartsByCustomerId(customerId);
        if (cartDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDTOs);
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable String cartId) {
        CartDTO cartDTO = cartService.getCartDTOById(cartId);
        if (cartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDTO);
    }
    @GetMapping("/find")
    public ResponseEntity<CartDTO> getPendingCartByCustomerId(@RequestParam String customerId) {
        Optional<CartDTO> cart = cartService.getPendingCartByCustomerId(customerId);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/cart")
    public ResponseEntity<CartDTO> createOrAddToPendingCart(@RequestParam String customerId, @RequestBody CartItemInput cartItemInput) {
        CartDTO cart = cartService.createOrAddToPendingCart(customerId, cartItemInput);
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

