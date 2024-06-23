package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.dto.CartItemDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {
    public static CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setPriceDebt(cartItem.getPriceDebt());
        cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
        cartItemDTO.setTotalDebt(cartItem.getTotalDebt());
        return cartItemDTO;
    }

    public static CartItem toEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemDTO.getCartItemId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());
        cartItem.setPriceDebt(cartItemDTO.getPriceDebt());
        cartItem.setTotalPrice(cartItemDTO.getTotalPrice());
        cartItem.setTotalDebt(cartItemDTO.getTotalDebt());
        // Map product if needed
        return cartItem;
    }
}
