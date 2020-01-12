package com.henry.grocery.promotion;

import java.time.LocalDateTime;

public class MultiProductPromotion extends ProductPromotion {

    private final String targetProductName;

    public MultiProductPromotion(String productName, String targetProductName, LocalDateTime validFrom, LocalDateTime validTill, double discount, int qualifyingQuantity) {
        super(productName, validFrom, validTill, discount, qualifyingQuantity);
        this.targetProductName = targetProductName;
    }

    public String getTargetProductName() {
        return targetProductName;
    }
}
