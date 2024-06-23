package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.domain.entity.Debt;
import com.example.usermanagement.dto.CartDTO;
import com.example.usermanagement.dto.CartItemDTO;
import com.example.usermanagement.dto.DebtDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        dto.setDownPayment(cart.getDownPayment());
        dto.setDebtAmount(cart.getDebtAmount());
        dto.setDebts(Optional.ofNullable(cart.getDebts())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDebtDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private CartItemDTO toCartItemDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setUnit(cartItem.getUnit());
        dto.setPrice(cartItem.getPrice());
        dto.setPriceDebt(cartItem.getPriceDebt());
        dto.setTotalPrice(cartItem.getTotalPrice());
        dto.setTotalDebt(cartItem.getTotalDebt());
        dto.setProductId(cartItem.getProduct().getProductId());
        dto.setProductName(cartItem.getProduct().getName());

        return dto;
    }

    public DebtDTO toDebtDTO(Debt debt) {
        DebtDTO dto = new DebtDTO();
        dto.setDebtId(debt.getDebtId());
        dto.setCartId(debt.getCart().getCartId());
        dto.setAmount(debt.getAmount());
        dto.setCreatedAt(debt.getCreatedAt());
        dto.setDueDate(debt.getDueDate());
        dto.setRemainingAmount(debt.getRemainingAmount());
        dto.setStatus(debt.getStatus());
        return dto;
    }
}
