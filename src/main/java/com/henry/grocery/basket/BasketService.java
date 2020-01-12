package com.henry.grocery.basket;

import com.henry.grocery.product.Product;
import com.henry.grocery.product.ProductService;
import com.henry.grocery.promotion.MultiProductPromotion;
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
                .reduce(0.0D, (itemTotal, item) -> itemTotal + applyPromotion(basket, checkoutDate, item), Double::sum);
        return MathUtils.round(basketTotal);
    }

    private double applyPromotion(Basket basket, LocalDateTime checkoutDate, Item item) {
        Optional<ProductPromotion> optionalPromotion = promotionService.findActivePromotionFor(checkoutDate, item.getProduct().getName());
        return optionalPromotion.map(productPromotion -> applyProductPromotion(basket, item, productPromotion)).orElseGet(item::getTotal);
    }

    private double applyProductPromotion(Basket basket, Item item, ProductPromotion promotion) {
        if (promotion instanceof MultiProductPromotion) {
            return applyMultiProductPromotion(basket, item, (MultiProductPromotion) promotion);
        } else {
            double itemTotal = item.getTotal();
            double discountRate = promotion.getDiscount();
            return applyDiscount(itemTotal, discountRate);
        }
    }

    private double applyMultiProductPromotion(Basket basket, Item item, MultiProductPromotion multiProductPromotion) {
        Optional<Item> optionalLinkedItem = basket.getItemsList().stream()
                .filter(basketItem -> basketItem.getProduct().getName().equals(multiProductPromotion.getTargetProductName()))
                .findAny();

        if (!optionalLinkedItem.isPresent()) {
            return item.getTotal();
        }
        Item linkedItem = optionalLinkedItem.get();
        int qualifiedQuantityForDiscount = linkedItem.getQuantity() / multiProductPromotion.getQualifyingQuantity();
        double discountRate = multiProductPromotion.getDiscount();

        if (item.getQuantity() <= qualifiedQuantityForDiscount) {
            return applyDiscount(item.getTotal(), discountRate);
        } else {
            int quantityDoesNotQualifyForDiscount = item.getQuantity() - qualifiedQuantityForDiscount;
            double totalWithoutDiscount = item.getTotalFor(quantityDoesNotQualifyForDiscount);
            double totalForQualifyingDiscount = item.getTotalFor(qualifiedQuantityForDiscount);
            double totalCost = totalWithoutDiscount + applyDiscount(totalForQualifyingDiscount, discountRate);
            return MathUtils.round(totalCost);
        }
    }

    private double applyDiscount(double grossTotal, double discountRate) {
        double appliedDiscount = grossTotal * discountRate;
        double totalCost = grossTotal - appliedDiscount;
        return MathUtils.round(totalCost);
    }
}
