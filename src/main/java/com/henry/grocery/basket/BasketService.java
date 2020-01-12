package com.henry.grocery.basket;

import com.henry.grocery.product.Product;
import com.henry.grocery.product.ProductService;
import com.henry.grocery.promotion.ProductPromotion;
import com.henry.grocery.promotion.PromotionService;
import com.henry.grocery.util.MathUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

public class BasketService {

    private final ProductService productService;
    private final PromotionService promotionService;

    public BasketService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public Basket addItem(Basket basket, String productName, int quantity) {
        Product product = productService.findByName(productName);
        basket.addItem(product, quantity);
        return new Basket(new HashMap<>(basket.getItems()));
    }

    public Basket checkout(Basket basket) {
        return checkout(basket, LocalDateTime.now());
    }

    public Basket checkout(Basket basket, LocalDateTime checkoutDate) {
        double basketTotal = calculateBasketTotal(basket, checkoutDate);
        return new Basket(basket.getItems(), basketTotal);
    }

    private double calculateBasketTotal(Basket basket, LocalDateTime checkoutDate) {
        double basketTotal = basket.getItemsList().stream()
                .reduce(0.0D, (itemTotal, item) -> itemTotal + applyPromotion(checkoutDate, item), Double::sum);
        return MathUtils.round(basketTotal);
    }

    private double applyPromotion(LocalDateTime checkoutDate, Item item) {
        Optional<ProductPromotion> optionalPromotion = promotionService.findActivePromotionFor(checkoutDate, item.getProduct().getName());
        return optionalPromotion.map(productPromotion -> applyProductPromotion(item, productPromotion)).orElseGet(item::getTotal);
    }

    private double applyProductPromotion(Item item, ProductPromotion promotion) {
        double itemTotal = item.getTotal();
        double discountRate = promotion.getDiscount();
        double appliedDiscount = itemTotal * discountRate;
        double totalCost = itemTotal - appliedDiscount;
        return MathUtils.round(totalCost);
    }
}
