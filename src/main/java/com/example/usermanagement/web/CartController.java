package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.entity.Debt;
import com.example.usermanagement.domain.service.CartService;
import com.example.usermanagement.dto.CartDTO;
import com.example.usermanagement.dto.DebtDTO;
import com.example.usermanagement.dto.user.input.CartItemInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;
    @GetMapping("")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        if (carts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carts);
    }
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

    @PutMapping("/complete")
    public ResponseEntity<Void> completeCart(@RequestParam String customerId,
                                             @RequestParam(required = false) BigDecimal downPayment) {
        cartService.completeCart(customerId, downPayment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/debt/payment")
    public ResponseEntity<List<DebtDTO>> makeDebtPayment(@RequestParam String cartId, @RequestParam BigDecimal payment) {
        List<DebtDTO> debt = cartService.makeDebtPayment(cartId, payment);
        return ResponseEntity.ok(debt);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeProductFromCart(@RequestParam String customerId, @RequestParam String productId) {
        cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/debt")
    public ResponseEntity<List<CartDTO>> getAllCartByDebt() {
        List<CartDTO> debtCart = cartService.getAllCartByDebt();
        if (debtCart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(debtCart);
    }
}

