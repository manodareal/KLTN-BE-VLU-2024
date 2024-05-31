package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.repo.CartRepository;
import com.example.usermanagement.domain.repo.CustomerRepository;
import com.example.usermanagement.domain.repo.ProductRepository;
import com.example.usermanagement.dto.CartDTO;
import com.example.usermanagement.dto.user.input.CartItemInput;
import com.example.usermanagement.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;
    @Transactional
    public CartDTO getCartDTOById(String cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        return optionalCart.map(cartMapper::toDTO).orElse(null);
    }
    @Transactional
    public List<CartDTO> getCartsByCustomerId(String customerId) {
        List<Cart> carts = cartRepository.findByCustomerId(customerId);
        return carts.stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public Optional<CartDTO> getPendingCartByCustomerId(String customerId) {
        Optional<Cart> cartOptional = cartRepository.findByCustomer_CustomerIdAndStatus(customerId, "Pending");
        return cartOptional.map(cartMapper::toDTO);
    }

    @Transactional
    public CartDTO createOrAddToPendingCart(String customerId, CartItemInput cartItemInput) {
        Product product = productRepository.findById(cartItemInput.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantityRemaining() < cartItemInput.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity remaining");
        }

        BigDecimal price = productService.getProductPriceByUnit(product, cartItemInput.getUnit());
        BigDecimal priceDebt = productService.getProductPriceDebtByUnit(product, cartItemInput.getUnit());

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemInput.getQuantity());
        cartItem.setPrice(price.multiply(BigDecimal.valueOf(cartItemInput.getQuantity())));
        cartItem.setUnit(cartItemInput.getUnit());

        Optional<Cart> optionalCart = cartRepository.findByCustomer_CustomerIdAndStatus(customerId, "Pending");

        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            existingCart.getCartItems().add(cartItem);
            cartItem.setCart(existingCart);
            log.info("Added item to existing pending cart for customer: " + customerId);
            return cartMapper.toDTO(cartRepository.save(existingCart));
        } else {
            Cart newCart = new Cart();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
            newCart.setCustomer(customer);
            newCart.getCartItems().add(cartItem);
            cartItem.setCart(newCart);
            log.info("Created new pending cart for customer: " + customerId);
            return cartMapper.toDTO(cartRepository.save(newCart));
        }
    }

    @Transactional
    public void completeCart(String customerId) {
        Cart cart = cartRepository.findByCustomer_CustomerIdAndStatus(customerId, "Pending")
                .orElseThrow(() -> new IllegalArgumentException("No pending cart found for customer: " + customerId));


        cart.setStatus("Done");
        cartRepository.save(cart);

        for (CartItem item : cart.getCartItems()) {
            productService.updateQuantityRemaining(item.getProduct().getProductId(), item.getQuantity());
        }

        log.info("Completed cart for customer: " + customerId);
    }

    public void removeProductFromCart(String customerId, String productId) {
        Cart cart = cartRepository.findByCustomer_CustomerIdAndStatus(customerId, "Pending")
                .orElseThrow(() -> new IllegalArgumentException("No pending cart found for customer: " + customerId));
        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart: " + productId));

        cart.getCartItems().remove(itemToRemove);
        cartRepository.save(cart);

        log.info("Removed product from cart: " + productId);
    }
}
