package com.henry.grocery.promotion;

import java.time.LocalDateTime;

public class MultiProductPromotion extends ProductPromotion {

    private final String targetProductName;
    private final int qualifyingQuantity;

    public MultiProductPromotion(String productName, String targetProductName, LocalDateTime validFrom, LocalDateTime validTill, double discount, int qualifyingQuantity) {
        super(productName, validFrom, validTill, discount);
        this.targetProductName = targetProductName;
        this.qualifyingQuantity = qualifyingQuantity;
    }

    public String getTargetProductName() {
        return targetProductName;
    }

    public int getQualifyingQuantity() {
        return qualifyingQuantity;
    }
}
