package com.henry.grocery.promotion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PromotionService {

    private final List<ProductPromotion> productPromotions = new ArrayList<>();

    public void addPromotion(ProductPromotion productPromotion) {
        productPromotions.add(productPromotion);
    }

    public List<ProductPromotion> getActivePromotions(LocalDateTime activeAt) {
        return productPromotions.stream()
                .filter((productPromotion -> productPromotion.getValidFrom().isBefore(activeAt) && productPromotion.getValidTill().isAfter(activeAt)))
                .collect(Collectors.toList());
    }

    public Optional<ProductPromotion> findActivePromotionFor(LocalDateTime activeAt, String productName) {
        return getActivePromotions(activeAt).stream()
                .filter((productPromotion -> productPromotion.getProductName().equals(productName)))
                .findAny();
    }
}
