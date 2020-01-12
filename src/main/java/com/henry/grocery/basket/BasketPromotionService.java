package com.henry.grocery.basket;

import com.henry.grocery.promotion.MultiProductPromotion;
import com.henry.grocery.promotion.ProductPromotion;
import com.henry.grocery.promotion.PromotionService;
import com.henry.grocery.util.MathUtils;

import java.time.LocalDateTime;
import java.util.Optional;

public class BasketPromotionService {

    private final PromotionService promotionService;

    public BasketPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public double applyPromotion(Basket basket, LocalDateTime checkoutDate, Item item) {
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
