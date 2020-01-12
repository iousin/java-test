package com.henry.grocery.basket;

import com.henry.grocery.product.Product;
import com.henry.grocery.product.ProductService;
import com.henry.grocery.util.MathUtils;

import java.time.LocalDateTime;
import java.util.HashMap;

public class BasketService {

    private final ProductService productService;
    private final BasketPromotionService basketPromotionService;

    public BasketService(ProductService productService, BasketPromotionService basketPromotionService) {
        this.productService = productService;
        this.basketPromotionService = basketPromotionService;
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
                .reduce(0.0D, (itemTotal, item) -> itemTotal + applyPromotion(basket, checkoutDate, item), Double::sum);
        return MathUtils.round(basketTotal);
    }

    private double applyPromotion(Basket basket, LocalDateTime checkoutDate, Item item) {
        return basketPromotionService.applyPromotion(basket, checkoutDate, item);
    }
}
