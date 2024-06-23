package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Cart;
import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.domain.entity.Customer;
import com.example.usermanagement.domain.entity.Debt;
import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.repo.*;
import com.example.usermanagement.dto.CartDTO;
import com.example.usermanagement.dto.DebtDTO;
import com.example.usermanagement.dto.user.input.CartItemInput;
import com.example.usermanagement.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final DebtRepository debtRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartDTO getCartDTOById(String cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        return optionalCart.map(cartMapper::toDTO).orElse(null);
    }
    @Transactional
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
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
    public List<CartDTO> getAllCartByDebt(){
        List<Cart> debtCart = cartRepository.findAllbyStatus("Debt");
        return debtCart.stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
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
        cartItem.setPrice(price);
        cartItem.setPriceDebt(priceDebt);
        cartItem.setTotalPrice(price.multiply(BigDecimal.valueOf(cartItemInput.getQuantity())));
        cartItem.setTotalDebt(priceDebt.multiply(BigDecimal.valueOf(cartItemInput.getQuantity())));
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
    public void completeCart(String customerId, BigDecimal downPayment) {
        Cart cart = cartRepository.findByCustomer_CustomerIdAndStatus(customerId, "Pending")
                .orElseThrow(() -> new IllegalArgumentException("No pending cart found for customer: " + customerId));

        if (downPayment != null) {
            BigDecimal totalDebtPrice = cart.getCartItems().stream()
                    .map(item -> productService.getProductPriceDebtByUnit(item.getProduct(), item.getUnit()).multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (downPayment.compareTo(totalDebtPrice) >= 0) {
                throw new IllegalArgumentException("Down payment must be less than total debt price");
            }

            Debt debt = new Debt();
            debt.setCart(cart);
            debt.setAmount(totalDebtPrice);
            debt.setRemainingAmount(totalDebtPrice.subtract(downPayment));
            debt.setDueDate(LocalDate.now().plusMonths(1)); // Set due date as 1 month from now
            debt.setStatus("Pending");

            debtRepository.save(debt);

            cart.setDownPayment(downPayment);
            cart.setDebtAmount(totalDebtPrice.subtract(downPayment));
            cart.setStatus("Debt");
        } else {
            cart.setStatus("Done");
        }

        cartRepository.save(cart);

        for (CartItem item : cart.getCartItems()) {
            productService.updateQuantityRemaining(item.getProduct().getProductId(), item.getQuantity());
        }

        log.info("Completed cart for customer: " + customerId);
    }

    @Transactional
    public List<DebtDTO> makeDebtPayment(String cartId, BigDecimal payment) {
        List<Debt> debts = debtRepository.findByCart_CartId(cartId);
        if (debts.isEmpty()) {
            throw new IllegalArgumentException("No debts found for cart: " + cartId);
        }

        for (Debt debt : debts) {
            if (payment.compareTo(debt.getRemainingAmount()) >= 0) {
                payment = payment.subtract(debt.getRemainingAmount());
                debt.setRemainingAmount(BigDecimal.ZERO);
                debt.setStatus("Paid");
            } else {
                debt.setRemainingAmount(debt.getRemainingAmount().subtract(payment));
                payment = BigDecimal.ZERO;
            }
            debtRepository.save(debt);

            if (payment.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
        }

        // Update cart status if all debts are paid
        boolean allDebtsPaid = debts.stream().allMatch(d -> d.getRemainingAmount().compareTo(BigDecimal.ZERO) == 0);
        if (allDebtsPaid) {
            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
            cart.setStatus("Done");
            cartRepository.save(cart);
        }

        log.info("Debt payment made for cart: " + cartId);

        // Convert the list of Debt entities to a list of DebtDTOs and return
        return debts.stream().map(cartMapper::toDebtDTO).collect(Collectors.toList());
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
