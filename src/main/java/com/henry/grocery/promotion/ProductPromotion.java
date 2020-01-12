package com.henry.grocery.promotion;

import java.time.LocalDateTime;

public class ProductPromotion {

    private final String productName;
    private final LocalDateTime validFrom;
    private final LocalDateTime validTill;
    private final double discount;

    public ProductPromotion(String productName, LocalDateTime validFrom, LocalDateTime validTill, double discount) {
        this.productName = productName;
        this.validFrom = validFrom;
        this.validTill = validTill;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTill() {
        return validTill;
    }

    public double getDiscount() {
        return discount;
    }
}
