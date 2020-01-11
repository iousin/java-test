package com.henry.grocery.basket;

import com.henry.grocery.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

    private final Product product;
    private final int quantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        BigDecimal total = new BigDecimal(product.getCost() * quantity);
        total.setScale(2, RoundingMode.CEILING);
        return total.doubleValue();
    }
}
