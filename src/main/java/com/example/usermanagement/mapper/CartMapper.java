package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.dto.CartDTO;
import com.example.usermanagement.dto.CartItemDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getCartId());
        dto.setStatus(cart.getStatus());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        dto.setCustomerId(cart.getCustomer().getCustomerId());
        dto.setCustomerName(cart.getCustomer().getFullName());
        dto.setCartItems(cart.getCartItems().stream()
                .map(this::toCartItemDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private CartItemDTO toCartItemDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setUnit(cartItem.getUnit());
        dto.setPrice(cartItem.getPrice());
        dto.setProductId(cartItem.getProduct().getProductId());
        dto.setProductName(cartItem.getProduct().getName());
        return dto;
    }

    public Cart toEntity(CartDTO dto) {
        return null;
    }
}
